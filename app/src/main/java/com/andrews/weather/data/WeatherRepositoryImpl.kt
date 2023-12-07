package com.andrews.weather.data

import com.andrews.weather.data.mappers.toCityWeatherForecast
import com.andrews.weather.data.mappers.toCurrentWeather
import com.andrews.weather.data.remote.OpenWeatherMapApi
import com.andrews.weather.domain.model.CurrentWeather
import com.andrews.weather.domain.model.DailyWeather
import com.andrews.weather.domain.repository.WeatherRepository

class WeatherRepositoryImpl(
    private val api: OpenWeatherMapApi
): WeatherRepository {

    override suspend fun getWeather(city: String): CurrentWeather =
        api.getWeatherDataForCity(city).toCurrentWeather()


    override suspend fun getWeatherForecast(city: String): List<DailyWeather> {
        return api.getWeatherForecastForCity(city).toCityWeatherForecast()
    }
}