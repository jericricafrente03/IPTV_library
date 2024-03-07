package com.bittelasia.network.domain.repository

import android.content.Context
import com.bittelasia.network.domain.model.home.App
import com.bittelasia.network.domain.model.home.ConfigData
import com.bittelasia.network.domain.model.home.CustomerData
import com.bittelasia.network.domain.model.home.WeatherData
import com.bittelasia.network.domain.model.home.Zones
import com.bittelasia.network.domain.model.license.response.LicenseDate
import com.bittelasia.network.domain.model.register.Register
import com.bittelasia.network.domain.model.stb.StbRegistration
import com.bittelasia.network.util.DataState
import kotlinx.coroutines.flow.Flow

interface MeshRepository {
    fun registerResult(stbRegistration: StbRegistration): Flow<DataState<Register>>
    suspend fun postResult(app: Context): Flow<DataState<LicenseDate>>

    fun configResult(): Flow<ConfigData>
    fun getCustomer(): Flow<CustomerData>
    fun getAppList(): Flow<List<App>>
    fun getThemes(): Flow<List<Zones>>
    fun getWeather(): Flow<List<WeatherData>>
    suspend fun getWeatherToday(): Flow<WeatherData>
    suspend fun getThemesByName(value: String): Flow<Zones>
}