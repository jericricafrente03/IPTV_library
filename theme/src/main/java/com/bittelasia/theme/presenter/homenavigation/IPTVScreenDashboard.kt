package com.bittelasia.theme.presenter.homenavigation

sealed class IPTVScreenDashboard(val title: String) {
    data object Home : IPTVScreenDashboard("home")
    data object Message : IPTVScreenDashboard("Messaging")
    data object Weather : IPTVScreenDashboard("Weather")
    data object Facilities : IPTVScreenDashboard("Amenities")
    data object Concierge : IPTVScreenDashboard("Concierge")
}