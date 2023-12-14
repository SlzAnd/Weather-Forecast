package com.andrews.weather.data

import android.location.Location
import com.andrews.weather.data.mappers.toCityWeatherForecast
import com.andrews.weather.data.mappers.toCurrentWeather
import com.andrews.weather.data.mappers.toTodayWeatherForecast
import com.andrews.weather.data.remote.OpenWeatherMapApi
import com.andrews.weather.domain.model.CurrentWeather
import com.andrews.weather.domain.model.DailyWeather
import com.andrews.weather.domain.repository.WeatherRepository
import com.andrews.weather.domain.util.Resource

class WeatherRepositoryImpl(
    private val api: OpenWeatherMapApi
) : WeatherRepository {

    override suspend fun getWeather(city: String): CurrentWeather {
        return api.getWeatherDataForCity(city).toCurrentWeather()
    }

    override suspend fun getWeatherForecast(city: String): List<DailyWeather> {
        return api.getWeatherForecastForCity(city).toCityWeatherForecast()
    }

    override suspend fun getWeatherForecastFromLocation(location: Location): Resource<Map<String, DailyWeather>> {
        return try {
            val map = api
                .getWeatherForecastForCityFromLocation(location.latitude, location.longitude)
                .toTodayWeatherForecast()
            Resource.Success(data = map)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(message = e.message ?: "API request was failed")
        }
    }
}

