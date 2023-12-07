package com.andrews.weather.domain.model

data class CurrentWeather(
    val cityName: String,
    val dayOfTheWeek: String,
    val temperature: Int,
    val time: String,
    val weatherDescription: String
)
