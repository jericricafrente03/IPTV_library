package com.bittelasia.network.data.remote


import com.bittelasia.core_datastore.model.STB
import com.bittelasia.network.domain.model.home.GetConfig
import com.bittelasia.network.domain.model.home.GetCustomer
import com.bittelasia.network.domain.model.home.GetIptvUi
import com.bittelasia.network.domain.model.home.GetTheme
import com.bittelasia.network.domain.model.home.GetWeeklyWeatherForecast
import com.bittelasia.network.domain.model.license.item.LicenseData
import com.bittelasia.network.domain.model.license.response.LicenseDate
import com.bittelasia.network.domain.model.register.Register
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface IptvListAPI {

    @GET("index.php/apicall/stb_register/{api}/{macAddress}/{room}/{type}/{version}")
    suspend fun registerResult(
        @Path("api") apiKey: String,
        @Path("macAddress") mcAddress: String,
        @Path("room") room: String,
        @Path("type") type: String = "0",
        @Path("version") version: String = "1"
    ): Register

    @POST("index.php/apicall/check_license")
    suspend fun getLicense(
        @Body requestLicense: LicenseData
    ): Response<LicenseDate>

    @GET("index.php/apicall/get_theme/{api}")
    suspend fun getThemes(
        @Path("api") apiKey: String = STB.API_KEY
    ): GetTheme

    @GET("index.php/apicall/get_config/{api}")
    suspend fun getConfig(
        @Path("api") apiKey: String = STB.API_KEY
    ): GetConfig

    @GET("index.php/apicall/get_iptv_ui/{api}")
    suspend fun getIptvUI(
        @Path("api") apiKey: String = STB.API_KEY
    ): GetIptvUi

    @GET("index.php/apicall/get_weekly_weather_forecast/{api}")
    suspend fun getWeather(
        @Path("api") apiKey: String = STB.API_KEY
    ): GetWeeklyWeatherForecast

    @GET("index.php/apicall/get_customer/{api}")
    suspend fun getCustomer(
        @Path("api") apiKey: String = STB.API_KEY,
    ): GetCustomer

}