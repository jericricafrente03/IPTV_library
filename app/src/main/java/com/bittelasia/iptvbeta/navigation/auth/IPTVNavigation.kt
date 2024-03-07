package com.bittelasia.iptvbeta.navigation.auth

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.bittelasia.theme.presenter.auth.SignupScreen
import com.bittelasia.theme.presenter.home.HomeScreen
import com.bittelasia.theme.presenter.splashscreen.SplashScreen

@Composable
fun IPTVNavigation(navHostController: NavHostController) {

    NavHost(navController = navHostController, startDestination = IPTVScreens.SplashScreen.title) {
        composable(route = IPTVScreens.SplashScreen.title) {
            SplashScreen(
                onAuthSuccess = {
                    navHostController.navigateSingleTopTo(IPTVScreens.Home.title)
                },
                onSplash = {
                    navHostController.navigateSingleTopTo(IPTVScreens.Login.title)
                }
            )
        }
        composable(route = IPTVScreens.Login.title) {
            SignupScreen(
                viewModel = hiltViewModel(),
                onAuthSuccess = {
                    navHostController.navigateSingleTopTo(IPTVScreens.Home.title)
                },
                onNavigateToPing = {
                    navHostController.navigateSingleTopTo(IPTVScreens.PingServer.title)
                }
            )
        }
        composable(route = IPTVScreens.Home.title) {
            HomeScreen()
        }
        composable(route = IPTVScreens.PingServer.title){
//            PingScreen()
        }
    }
}


fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) {
        popUpTo(
            this@navigateSingleTopTo.graph.findStartDestination().id
        ) {
            saveState = true
            inclusive = true
        }
        launchSingleTop = true
        restoreState = true
    }