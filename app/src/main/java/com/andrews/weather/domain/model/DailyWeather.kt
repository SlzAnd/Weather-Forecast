package com.andrews.weather.domain.model

import com.andrews.weather.domain.WeatherIcon

data class DailyWeather(
    val dayOfTheWeek: String,
    val icon: WeatherIcon,
    val humidity: Int,
    val dayTemperature: Int,
    val nightTemperature: Int,
    val description: String,
)
