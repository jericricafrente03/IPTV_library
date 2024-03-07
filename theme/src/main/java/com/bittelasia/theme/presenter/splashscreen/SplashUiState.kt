package com.bittelasia.theme.presenter.splashscreen

sealed class SplashUiState {
    data object Authenticated : SplashUiState()
    data object Splash: SplashUiState()
}
