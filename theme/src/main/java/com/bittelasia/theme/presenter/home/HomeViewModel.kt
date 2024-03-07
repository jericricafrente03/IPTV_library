package com.bittelasia.theme.presenter.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bittelasia.network.domain.manager.Command.alive
import com.bittelasia.network.domain.manager.Command.broadcast
import com.bittelasia.network.domain.manager.Command.concierge
import com.bittelasia.network.domain.manager.Command.configuration
import com.bittelasia.network.domain.manager.Command.facility
import com.bittelasia.network.domain.manager.Command.facilityCategory
import com.bittelasia.network.domain.manager.Command.guest
import com.bittelasia.network.domain.manager.Command.iptv
import com.bittelasia.network.domain.manager.Command.message
import com.bittelasia.network.domain.manager.Command.reset
import com.bittelasia.network.domain.manager.Command.resetLicense
import com.bittelasia.network.domain.manager.Command.settings
import com.bittelasia.network.domain.manager.Command.theme
import com.bittelasia.network.domain.manager.Command.tv
import com.bittelasia.network.domain.manager.Command.xmppToAPI
import com.bittelasia.network.domain.model.home.App
import com.bittelasia.network.domain.model.home.ConfigData
import com.bittelasia.network.domain.model.home.CustomerData
import com.bittelasia.network.domain.model.home.WeatherData
import com.bittelasia.network.domain.model.home.Zones
import com.bittelasia.network.domain.repository.MeshRepository
import com.bittelasia.service_xmpp.extension.XmppManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val dataAPI: MeshRepository,
    private val xmppManager: XmppManager
) : ViewModel() {

    private val _config: MutableStateFlow<ConfigData?> = MutableStateFlow(null)
    private val _appList: MutableStateFlow<List<App>?> = MutableStateFlow(emptyList())
    private val _weather: MutableStateFlow<WeatherData?> = MutableStateFlow(null)
    private val _customer: MutableStateFlow<CustomerData?> = MutableStateFlow(null)

    private val _top: MutableStateFlow<Zones?> = MutableStateFlow(null)
    private val _center: MutableStateFlow<Zones?> = MutableStateFlow(null)
    private val _bottom: MutableStateFlow<Zones?> = MutableStateFlow(null)

    init {
        viewModelScope.launch(Dispatchers.IO) {
            xmppManager.incomingMessage().collect {
                when (xmppToAPI(it)) {
                    theme -> {
                        homeUiThemes()
                    }
                    configuration -> {
                        configuration()
                    }
                    iptv -> {
                        application()
                    }
                    guest -> {
                        dataAPI.getCustomer().collectLatest {}
                    }
                    alive -> {}
                    reset -> {}
                    settings -> {}
                    concierge -> {}
                    broadcast -> {}
                    resetLicense -> {}
                    tv -> {}
                    facility -> {}
                    facilityCategory -> {}
                    message -> {}
                    else -> {}
                }
            }
        }
    }

    val uiState: StateFlow<HomeUiState> = combine(
        _config.filterNotNull(),
        _appList.filterNotNull(),
        _weather.filterNotNull(),
        _customer.filterNotNull()
    ) { config, appList, weather, cs ->
        HomeUiState.Ready(config, appList, weather, cs)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = HomeUiState.Loading
    )
    val themesState: StateFlow<ThemesUiState> = combine(
        _top.filterNotNull(),
        _center.filterNotNull(),
        _bottom.filterNotNull()
    ) { top, center, bottom ->
        ThemesUiState.Ready(top, center, bottom)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = ThemesUiState.Loading
    )

    fun initHomeAPI() {
        viewModelScope.launch(Dispatchers.IO) {
            val init = listOf(
                async(Dispatchers.IO) { configuration() },
                async(Dispatchers.IO) { application() },
                async(Dispatchers.IO) { dailyWeather() },
                async(Dispatchers.IO) { checkGuest() },
                async(Dispatchers.IO) { homeUiThemes() }
            )
            init.awaitAll()
        }
    }

    private fun configuration() = viewModelScope.launch(Dispatchers.IO) {
        dataAPI.configResult().collectLatest {
            _config.value = it
        }
    }
    private fun application() = viewModelScope.launch(Dispatchers.IO) {
        dataAPI.getAppList().collectLatest {
            _appList.value = it
        }
    }
    private fun dailyWeather() = viewModelScope.launch(Dispatchers.IO) {
        dataAPI.getWeatherToday().collectLatest {
            _weather.value = it
        }
    }
    private fun checkGuest() = viewModelScope.launch(Dispatchers.IO) {
        dataAPI.getCustomer().collectLatest {
            _customer.value = it
        }
    }
    private fun homeUiThemes() = viewModelScope.launch(Dispatchers.IO) {
        dataAPI.getThemes().collectLatest { zone ->
            _top.value = zone.find { it.section == "Topbar" }
            _center.value = zone.find { it.section == "WelcomeMessageZone" }
            _bottom.value = zone.find { it.section == "AppListZone" }
        }
    }
}

sealed interface HomeUiState {
    data object Loading : HomeUiState
    data object Error : HomeUiState
    data class Ready(
        val config: ConfigData? = null,
        val app: List<App>? = emptyList(),
        val weather: WeatherData? = null,
        val customer: CustomerData? = null
    ) : HomeUiState
}

sealed interface ThemesUiState {
    data object Loading : ThemesUiState
    data class Ready(
        val topTheme: Zones? = null,
        val welcomeText: Zones? = null,
        val appTheme: Zones? = null
    ) : ThemesUiState
}