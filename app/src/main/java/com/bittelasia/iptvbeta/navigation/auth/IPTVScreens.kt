package com.bittelasia.iptvbeta.navigation.auth


sealed class IPTVScreens(val title: String){
    data object SplashScreen : IPTVScreens("splashScreen")
    data object Login : IPTVScreens("login")
    data object Home : IPTVScreens("home")
    data object PingServer : IPTVScreens("ping")
}