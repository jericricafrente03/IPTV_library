package com.bittelasia.theme.presenter.home.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.bittelasia.network.domain.model.home.ConfigData
import com.bittelasia.network.domain.model.home.CustomerData
import com.bittelasia.network.domain.model.home.WeatherData
import com.bittelasia.network.domain.model.home.Zones
import com.bittelasia.theme.presenter.home.top.HomeTop
import com.bittelasia.theme.presenter.home.top.HomeTopOutside

@Composable
fun BackUi(
    navController: NavHostController,
    zones: Zones?,
    weather: WeatherData?,
    customerData: CustomerData?,
    configData: ConfigData?,
    content: @Composable (NavHostController) -> Unit
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center,
        content = {
            if (currentDestination?.route != "home") {
                HomeBackground(imageUrl = configData?.bg)
            }
            Column(modifier = Modifier.fillMaxSize()) {
                TopBarRouting(
                    routes = currentDestination?.route,
                    zones = zones,
                    weather = weather,
                    customerData = customerData,
                    configData = configData
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    content(navController)
                }
            }
        }
    )
}

@Composable
private fun TopBarRouting(
    routes: String?,
    zones: Zones?,
    weather: WeatherData?,
    customerData: CustomerData?,
    configData: ConfigData?
) {
    if (routes != "home") {
        HomeTopOutside(
            zones = zones,
            weather = weather,
            customerData = customerData,
            configData = configData,
            pageTitleTxt = routes
        )
    } else {
        Spacer(modifier = Modifier.height(50.dp))
        HomeTop(
            zones = zones,
            weather = weather,
            customerData = customerData,
            configData = configData
        )
    }
}