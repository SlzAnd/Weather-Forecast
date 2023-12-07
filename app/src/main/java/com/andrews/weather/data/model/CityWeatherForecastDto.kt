package com.andrews.weather.data.model

data class CityWeatherForecastDto(
    val city: CityX,
    val list: List<WeatherForecast>,
)