package com.andrews.weather.data.mappers

import com.andrews.weather.data.model.CitiesDto
import com.andrews.weather.domain.model.CityInfo
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter


fun CitiesDto.toCityInfoList(): List<CityInfo> =
    list.map { cityDto ->
        CityInfo(
            id = cityDto.id,
            name = cityDto.name,
            time = convertUnixTimestampToTime(cityDto.dt.toLong()),
            temperature = cityDto.main.temp.toInt()
        )
    }

private fun convertUnixTimestampToTime(unixTimestamp: Long): String {
    val localDateTime = LocalDateTime.ofInstant(
        Instant.ofEpochMilli(unixTimestamp * 1000),
        ZoneId.systemDefault()
    )
    val formatter = DateTimeFormatter.ofPattern("HH:mm")
    return localDateTime.format(formatter)
}