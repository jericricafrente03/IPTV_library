package com.bittelasia.network.data.repository

import android.content.Context
import com.bittelasia.core_datastore.model.STB
import com.bittelasia.network.data.local.MeshDataBase
import com.bittelasia.network.data.remote.IptvListAPI
import com.bittelasia.network.domain.model.home.WeatherData
import com.bittelasia.network.domain.model.home.Zones
import com.bittelasia.network.domain.model.license.item.LicenseData
import com.bittelasia.network.domain.model.license.item.LicenseKey
import com.bittelasia.network.domain.model.license.response.LicenseDate
import com.bittelasia.network.domain.model.register.Register
import com.bittelasia.network.domain.model.stb.StbRegistration
import com.bittelasia.network.domain.repository.MeshRepository
import com.bittelasia.network.util.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import java.net.NoRouteToHostException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class MeshRepositoryImpl @Inject constructor(
    meshDataBase: MeshDataBase,
    private val api: IptvListAPI,
) : MeshRepository {
    private val dataBase = meshDataBase.homeUiDao()

    override fun registerResult(stbRegistration: StbRegistration): Flow<DataState<Register>> =
        flow {
            try {
                emit(DataState.Loading())
                stbRegistration.apply {
                    val registerUser = api.registerResult(
                        apiKey = STB.API_KEY,
                        mcAddress = mac,
                        room = room
                    )
                    emit(DataState.Success(registerUser))
                }
            } catch (e: Exception) {
                when (e) {
                    is NoRouteToHostException,
                    is HttpException,
                    is IOException -> {
                        emit(DataState.Error(message = e.message ?: "Error"))
                    }

                    else -> throw e
                }
            }
        }

    override suspend fun postResult(app: Context): Flow<DataState<LicenseDate>> = flow {
        try {
            emit(DataState.Loading())
            val key = LicenseData(LicenseKey(STB.API_KEY))
            val getData = api.getLicense(key)
            emit(DataState.Success(getData.body()))
        } catch (e: Exception) {
            emit(DataState.Error(message = e.message ?: "Error"))
        }
    }

    override fun configResult() = flow {
        val getData = api.getConfig()
        dataBase.deleteConfigData()
        dataBase.insertAppData(getData.data)
        val dbResult = dataBase.getConfigData()
        emit(dbResult)
    }

    override fun getCustomer() = flow {
        val getData = api.getCustomer()
        dataBase.deleteCustomer()
        dataBase.insertCustomer(getData.data)
        val dbResult = dataBase.getCustomer()
        emit(dbResult)
    }

    override fun getAppList() = flow {
        val getData = api.getIptvUI()
        dataBase.deleteApp()
        dataBase.insertApp(getData.apps)
        val dbResult = dataBase.getAllApp()
        emit(dbResult)
    }

    override fun getThemes() = flow {
        val getData = api.getThemes()
        dataBase.deleteAllThemes()
        dataBase.insertThemes(getData.zones)
        val dbResult = dataBase.getAllThemes()
        emit(dbResult)
    }

    override fun getWeather() = flow {
        val getData = api.getWeather()
        dataBase.deleteWeather()
        dataBase.insertWeather(getData.data)
        val dbResult = dataBase.getWeather()
        emit(dbResult)
    }

    override suspend fun getWeatherToday(): Flow<WeatherData> = flow {
        val weather = api.getWeather()
        dataBase.deleteWeather()
        dataBase.insertWeather(weather.data)
        val todayDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        val dbResult = dataBase.getWeather()
        val currentWeather = dbResult.find { date -> date.date == todayDate } ?: dbResult.last()
        emit(currentWeather)
    }

    override suspend fun getThemesByName(value: String): Flow<Zones> = flow {
        val dbResult = dataBase.getAllThemes()
        val zone = dbResult.find {  zone -> zone.section == value } ?: dbResult.last()
        emit(zone)
    }


}