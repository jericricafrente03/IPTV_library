package com.bittelasia.network.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bittelasia.network.domain.model.home.App
import com.bittelasia.network.domain.model.home.ConfigData
import com.bittelasia.network.domain.model.home.CustomerData
import com.bittelasia.network.domain.model.home.WeatherData
import com.bittelasia.network.domain.model.home.Zones
import com.bittelasia.network.domain.model.home.dao.HomeDao

@Database(
    entities = [
        ConfigData::class,
        CustomerData::class,
        App::class,
        Zones::class,
        WeatherData::class
    ], version = 1 , exportSchema = false
)
abstract class MeshDataBase : RoomDatabase() {
    abstract fun homeUiDao(): HomeDao
}