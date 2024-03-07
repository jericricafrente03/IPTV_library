package com.bittelasia.network.domain.model.home

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import javax.annotation.concurrent.Immutable

@Entity
@Immutable
data class WeatherData(
    val date: String,
    val description: String,
    @SerializedName("dew_point") val dewPoint: String,
    val humidity: String,
    val icon: String,
    @PrimaryKey val id: String,
    val pressure: String,
    val sunrise: String,
    val sunset: String,
    @SerializedName("temp_day") val tempDay: String,
    @SerializedName("temp_eve") val tempEve: String,
    @SerializedName("temp_max") val tempMax: String,
    @SerializedName("temp_min") val tempMin: String,
    @SerializedName("temp_morn") val tempMorn: String,
    @SerializedName("temp_night") val tempNight: String,
    @SerializedName("wind_speed") val windSpeed: String
)