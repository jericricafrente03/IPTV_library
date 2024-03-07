package com.bittelasia.theme.presenter.weather

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.bittelasia.network.domain.model.home.WeatherData
import com.bittelasia.network.domain.model.home.Zones
import com.bittelasia.theme.presenter.home.main.ThemeBackground

@Composable
fun WeatherWeekly(
    modifier: Modifier = Modifier,
    weather: List<WeatherData>?,
    zone: Zones?,
    navHostController: NavHostController
) {
    if (zone != null) {
        ThemeBackground(zone = zone, modifier = modifier) {
            Column {
                LazyRow(
                    modifier = Modifier
                        .weight(.7f)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.Top,
                    horizontalArrangement = Arrangement.Center
                ) {
                    weather?.let { data ->
                        items(data.size) { list ->
                            val item = data[list]
                            DailyWeatherItem(
                                weatherUpdate = item,
                                color = zone.textColor
                            )
                        }
                    }
                }
                Box(
                    modifier = Modifier
                        .weight(.3f)
                        .fillMaxWidth(), contentAlignment = Alignment.CenterStart,
                ) {
                    /*CustomBackButton(navHostController = navHostController)*/
                }
            }
        }
    }
}