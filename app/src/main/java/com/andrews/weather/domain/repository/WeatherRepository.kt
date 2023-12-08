package com.andrews.weather.domain.repository

import com.andrews.weather.domain.model.CurrentWeather
import com.andrews.weather.domain.model.DailyWeather

interface WeatherRepository {
    suspend fun getWeather(city: String): CurrentWeather

    suspend fun getWeatherForecast(city: String): List<DailyWeather>
}