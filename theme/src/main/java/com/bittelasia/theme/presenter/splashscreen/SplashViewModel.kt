package com.bittelasia.theme.presenter.splashscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bittelasia.core_datastore.DataStoreOperations
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(private val pref: DataStoreOperations) : ViewModel() {
    private val _uiState = MutableStateFlow<SplashUiState>(SplashUiState.Splash)
    val uiState: StateFlow<SplashUiState> = _uiState

    init {
        isLoggedIn()
    }

    private fun isLoggedIn() = viewModelScope.launch {
        pref.readStbFlow(Dispatchers.IO){
            _uiState.value =
                if (it.FIRST_RUN == "1") SplashUiState.Authenticated else SplashUiState.Splash
        }
    }
}