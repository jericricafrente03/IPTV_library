package com.bittelasia.theme.presenter.weather

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.bittelasia.network.domain.model.home.WeatherData
import com.bittelasia.network.domain.model.home.Zones

@Composable
fun WeatherScreen(
    viewModel: WeatherViewModel = hiltViewModel(),
    navHostController: NavHostController
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = Unit) {
        viewModel.initWeatherAPI()
    }

    when (val s = uiState) {
        is WeatherUiState.Ready -> {
            Weather(
                todayZone = s.todayZone,
                weeklyZone = s.listWeatherZone,
                list = s.listWeather,
                today = s.weather,
                navHostController = navHostController,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(all = 20.dp)
            )
        }
        is WeatherUiState.Loading -> Log.v("meme", "1weather data loading ->")
        is WeatherUiState.Error -> Log.v("meme", "data error ->")
    }
}

@Composable
fun Weather(
    todayZone: Zones?,
    weeklyZone: Zones?,
    list: List<WeatherData>?,
    today: WeatherData?,
    modifier: Modifier = Modifier,
    navHostController: NavHostController
) {

    Column(modifier = modifier) {
        WeatherTodayContent(
            weather = today,
            zone = todayZone,
            modifier = Modifier.weight(.45f)
        )
        WeatherWeekly(
            modifier = Modifier.weight(.55f),
            zone = weeklyZone,
            weather = list,
            navHostController = navHostController
        )
    }
}