package com.andrews.weather.data.mappers

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import com.andrews.weather.data.model.CitiesDto
import com.andrews.weather.domain.model.CityInfo
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date

fun CitiesDto.toCityInfoList(): List<CityInfo> =
    list.map { cityDto ->
        CityInfo(
            id = cityDto.id,
            name = cityDto.name,
            time = convertUnixTimestampToTime(cityDto.dt.toLong()),
            temperature = cityDto.main.temp.toInt()
        )
    }


@SuppressLint("SimpleDateFormat")
private fun convertUnixTimestampToTime(unixTimestamp: Long): String {
    val date = Date(unixTimestamp * 1000)
    val format = SimpleDateFormat("HH:mm")
    return format.format(date)
}