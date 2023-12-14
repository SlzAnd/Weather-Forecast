package com.andrews.weather.domain.repository

import android.location.Location
import com.andrews.weather.domain.model.CurrentWeather
import com.andrews.weather.domain.model.DailyWeather
import com.andrews.weather.domain.util.Resource

interface WeatherRepository {
    suspend fun getWeather(city: String): CurrentWeather

    suspend fun getWeatherForecast(city: String): List<DailyWeather>

    suspend fun getWeatherForecastFromLocation(location: Location): Resource<Map<String, DailyWeather>>
}