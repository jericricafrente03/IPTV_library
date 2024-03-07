package com.bittelasia.theme.presenter.homenavigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.bittelasia.network.domain.model.home.ConfigData
import com.bittelasia.network.domain.model.home.CustomerData
import com.bittelasia.network.domain.model.home.WeatherData
import com.bittelasia.network.domain.model.home.Zones
import com.bittelasia.theme.presenter.home.main.BackUi
import com.bittelasia.theme.presenter.weather.WeatherScreen

@Composable
fun HomeNavigation(
    navController: NavHostController,
    zones: Zones?,
    weather: WeatherData?,
    customerData: CustomerData?,
    configData: ConfigData?
) {
    BackUi(
        navController = navController,
        zones = zones,
        weather = weather,
        configData = configData,
        customerData = customerData
    ) {
        NavHost(
            navController = navController,
            startDestination = IPTVScreenDashboard.Home.title
        ) {
            composable(IPTVScreenDashboard.Home.title) {
//                HomePageContent(viewModel = viewModel)
                Box(modifier = Modifier.fillMaxSize()) {

                }
            }
            composable(IPTVScreenDashboard.Weather.title) {
                WeatherScreen(navHostController = navController)
            }
            composable(IPTVScreenDashboard.Message.title) {
                Box(modifier = Modifier.fillMaxSize()) {

                }
//                MessageContent(viewModel = viewModel, navHostController = navController)
            }
            composable(IPTVScreenDashboard.Facilities.title) {
//                InfoContent(viewModel = viewModel, navHostController = navController)
            }
            composable(IPTVScreenDashboard.Concierge.title) {
//                ConciergeContent(viewModel = viewModel, navHostController = navController)
            }
        }
    }
}
