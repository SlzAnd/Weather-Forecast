package com.andrews.weather.data.model

data class WeatherForecast (
    val dt: Int,
    val dt_txt: String,
    val main: Main,
    val weather: List<Weather>,
)