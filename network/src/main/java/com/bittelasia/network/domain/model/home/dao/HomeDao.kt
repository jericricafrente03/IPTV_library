package com.bittelasia.network.domain.model.home.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bittelasia.network.domain.model.home.App
import com.bittelasia.network.domain.model.home.ConfigData
import com.bittelasia.network.domain.model.home.CustomerData
import com.bittelasia.network.domain.model.home.WeatherData
import com.bittelasia.network.domain.model.home.Zones

@Dao
interface HomeDao {
    @Query("SELECT * FROM config_table")
    fun getConfigData(): ConfigData

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAppData(c: ConfigData)

    @Query("DELETE FROM config_table")
    suspend fun deleteConfigData()

    @Query("SELECT * FROM customerdata")
    fun getCustomer(): CustomerData

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCustomer(cs: CustomerData)

    @Query("DELETE FROM customerdata")
    suspend fun deleteCustomer()

    @Query("SELECT * FROM applist")
    fun getAllApp(): List<App>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertApp(app: List<App>)

    @Query("DELETE FROM applist")
    suspend fun deleteApp()

    @Query("SELECT * FROM zone")
    fun getAllThemes(): List<Zones>

    @Query("SELECT * FROM zone WHERE section=:layout")
    suspend fun getLayout(layout: String) : Zones?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertThemes(theme: List<Zones>)

    @Query("DELETE FROM zone")
    suspend fun deleteAllThemes()

    @Query("SELECT * FROM weatherdata")
    fun getWeather(): List<WeatherData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeather(weather: List<WeatherData>)

    @Query("SELECT * FROM weatherdata WHERE date = :date")
    suspend fun getWeatherByDate(date: String): WeatherData?

    @Query("DELETE FROM weatherdata")
    suspend fun deleteWeather()
}