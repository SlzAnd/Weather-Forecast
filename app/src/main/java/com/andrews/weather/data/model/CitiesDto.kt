package com.andrews.weather.data.model

data class CitiesDto(
    val calctime: Double,
    val cnt: Int,
    val cod: Int,
    val list: List<City>
)