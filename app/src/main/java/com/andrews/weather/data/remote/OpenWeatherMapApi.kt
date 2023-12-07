package com.andrews.weather.data.remote

import com.andrews.weather.data.model.CitiesDto
import com.andrews.weather.data.model.CityWeatherDto
import com.andrews.weather.data.model.CityWeatherForecastDto
import com.andrews.weather.util.ConstValues.API_KEY
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherMapApi {

    @GET("data/2.5/weather?appid=$API_KEY")
    suspend fun getWeatherDataForCity(
        @Query("q") cityName: String,
        @Query("units") units: String = "metric",
    ): CityWeatherDto

    @GET("data/2.5/forecast?appid=$API_KEY")
    suspend fun getWeatherForecastForCity(
        @Query("q") cityName: String,
        @Query("units") units: String = "metric",
    ): CityWeatherForecastDto

    @GET("data/2.5/box/city?appid=$API_KEY")
    suspend fun getListOfCities(
        @Query("bbox") bbox: String
    ): CitiesDto
}