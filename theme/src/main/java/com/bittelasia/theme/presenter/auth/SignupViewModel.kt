package com.bittelasia.theme.presenter.auth

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bittelasia.core_datastore.DataStoreOperations
import com.bittelasia.core_datastore.model.STB
import com.bittelasia.network.domain.manager.BaseUrlInterceptor
import com.bittelasia.network.domain.model.license.item.LicenseData
import com.bittelasia.network.domain.model.license.item.LicenseKey
import com.bittelasia.network.domain.model.register.Register
import com.bittelasia.network.domain.model.stb.StbRegistration
import com.bittelasia.network.domain.repository.MeshRepository
import com.bittelasia.network.util.DataState
import com.bittelasia.network.util.device.NetworkUtil
import com.bittelasia.theme.presenter.auth.validators.ValidatorFactory
import com.bittelasia.theme.presenter.auth.validators.utils.AuthParams
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val application: Application,
    private val validatorFactory: ValidatorFactory,
    private val repository: MeshRepository,
    private val pref: DataStoreOperations
) : ViewModel() {

    private val _uiState = MutableStateFlow<SignupUiState>(SignupUiState.Default())
    val uiState: StateFlow<SignupUiState> = _uiState

    fun onEvent(uiEvent: SignupUiEvent) {
        when (uiEvent) {
            is SignupUiEvent.IpChanged -> updateState { it.copy(ipAddress = uiEvent.ip) }
            is SignupUiEvent.PortChange -> updateState { it.copy(port = uiEvent.port) }
            is SignupUiEvent.RoomChanged -> updateState { it.copy(room = uiEvent.room) }
            SignupUiEvent.Submit -> {
                if (areInputsValid()) {
                    signup()
                }
            }

            else -> {
                Unit
            }
        }
    }

    private fun updateState(update: (SignupUiState.Default) -> SignupUiState.Default) {
        _uiState.value = (_uiState.value as? SignupUiState.Default)?.let(update)
            ?: _uiState.value
    }

    private fun areInputsValid(): Boolean {
        val ui = (_uiState.value as? SignupUiState.Default) ?: return false
        val ipAddError = validatorFactory.get(AuthParams.IP_ADDRESS).validate(ui.ipAddress)
        val portError = validatorFactory.get(AuthParams.PORT).validate(ui.port)
        val roomError = validatorFactory.get(AuthParams.ROOM).validate(ui.room)

        val hasError = listOf(
            ipAddError,
            portError,
            roomError
        ).any { !it.isValid }

        _uiState.value = ui.copy(
            ipAddressError = ipAddError.errorMessage,
            portError = portError.errorMessage,
            roomError = roomError.errorMessage,
        )
        return !hasError
    }

    private fun signup() = viewModelScope.launch {
        try {
            val ui = (_uiState.value as? SignupUiState.Default) ?: return@launch

            application.apply {
                val mac = async { return@async NetworkUtil.macAddress(this@apply) }
                val key = async {
                    return@async LicenseData(LicenseKey(NetworkUtil.hashedDeviceMac(this@apply)))
                }

                pref.saveStbState(
                    stb = STB.apply {
                        HOST = "http://${ui.ipAddress}"
                        PORT = ui.port
                        ROOM = ui.room
                        MAC_ADDRESS = mac.await()
                        API_KEY = key.await().key.apiKey
                    }
                )
                BaseUrlInterceptor.setBaseUrl("${STB.HOST}:${STB.PORT}/")
                repository.registerResult(
                    stbRegistration = StbRegistration(
                        ip = STB.HOST,
                        port = STB.PORT,
                        room = STB.ROOM,
                        mac = STB.MAC_ADDRESS,
                        apiKey = STB.API_KEY
                    )
                ).onEach { state ->
                    when (state) {
                        is DataState.Loading -> _uiState.value =
                            SignupUiState.Default(isLoading = true)

                        is DataState.Error -> _uiState.value =
                            SignupUiState.Default(signupError = state.message)

                        is DataState.Success -> {
                            state.data?.regResult.let { result ->
                                when (result?.result) {
                                    "success" -> {
                                        state.data?.let { isAuthenticated(this, register = it) }
                                        _uiState.value = SignupUiState.Authenticated
                                    }

                                    "failed" -> {
                                        _uiState.value = SignupUiState.Default(
                                            signupError = result.reasons
                                        )
                                    }
                                }
                            }
                        }
                    }
                }.launchIn(viewModelScope)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun isAuthenticated(app: Application, register: Register) {
        viewModelScope.launch {
            repository.postResult(app).collect { license ->
                license.data?.data.let {
                    pref.saveStbState(
                        stb = STB.apply {
                            USERNAME = register.jidUser
                            PASSWORD = register.jidPass
                            DEV_ID = register.deviceId
                            FIRST_RUN = "1"
                            END_DATE = it?.endDate ?: ""
                            REMAINING_DAYS = it?.remainingDays ?: ""
                        }
                    )
                }
            }
        }
    }
}
