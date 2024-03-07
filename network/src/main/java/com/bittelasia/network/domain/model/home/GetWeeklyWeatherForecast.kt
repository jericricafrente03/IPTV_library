package com.bittelasia.network.domain.model.home

data class GetWeeklyWeatherForecast(
    val `data`: List<WeatherData>
)