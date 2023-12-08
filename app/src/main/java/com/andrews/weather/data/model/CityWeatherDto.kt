package com.andrews.weather.data.model

data class CityWeatherDto(
    val name: String,
    val dt: Int,
    val main: Main,
    val weather: List<Weather>,
    val timezone: Int
)
