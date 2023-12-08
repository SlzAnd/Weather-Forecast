package com.andrews.weather.ui.weather_screen

import com.andrews.weather.domain.model.CurrentWeather
import com.andrews.weather.domain.model.DailyWeather

data class WeatherState(
    val cityName: String = "",
    val currentWeather: CurrentWeather? = null,
    val dailyWeatherList: List<DailyWeather> = emptyList(),
)
