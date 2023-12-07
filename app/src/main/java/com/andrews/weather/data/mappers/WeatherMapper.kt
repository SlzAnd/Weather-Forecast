package com.andrews.weather.data.mappers

import com.andrews.weather.data.model.CityWeatherDto
import com.andrews.weather.data.model.CityWeatherForecastDto
import com.andrews.weather.data.model.WeatherForecast
import com.andrews.weather.domain.WeatherIcon
import com.andrews.weather.domain.model.CurrentWeather
import com.andrews.weather.domain.model.DailyWeather
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.util.Date
import java.util.Locale
import java.util.TimeZone

fun CityWeatherForecastDto.toCityWeatherForecast(): List<DailyWeather> {
    return list
        .filter { convertDateTimeTxtToLocalDateTime(it.dt_txt).dayOfYear != LocalDateTime.now().dayOfYear } // remove current day
        .mapIndexed { index, forecast ->
            val map = mutableMapOf<Int, MutableList<WeatherForecast>>()
            // grouping forecast by days, we take 4 days starts from tomorrow.
            // 5's day excluded(because it has not full forecast)
            // our list contains forecast for each 3 hour. So full day contains 7 time points.
            // We start from 1 index it's 03:00 for getting 00:00 as last point
            when(index) {
                in 1..8 -> {
                    map[0]?.add(forecast)
                }
                in 9..16 -> {
                    map[1]?.add(forecast)
                }
                in 17..24 -> {
                    map[2]?.add(forecast)
                }
                in 25..32 -> {
                    map[3]?.add(forecast)
                }
                else -> {}
            }
            return@mapIndexed map
        }.map {dayMap ->
            val resultList = mutableListOf<DailyWeather>()
            dayMap.forEach { (_, list) ->
                resultList.add(DailyWeather(
                    dayOfTheWeek = convertDateTimeTxtToDayOfTheWeek(list[1].dt_txt),
                    icon = WeatherIcon.fromIconCode(list[3].weather[0].id), // weather icon 12:00
                    humidity = list[3].main.humidity, // humidity 12:00
                    dayTemperature = list[3].main.temp.toInt(), // day temperature 12:00
                    nightTemperature = list[7].main.temp.toInt(), // night temperature 00:00
                ))
            }
            return resultList
        }
}

fun CityWeatherDto.toCurrentWeather(): CurrentWeather {
    return CurrentWeather(
        cityName = this.name,
        dayOfTheWeek = convertDateTimeToDayOfTheWeek(this.dt.toLong()),
        temperature = this.main.temp.toInt(),
        time = convertDateTimeToTime(this.dt.toLong()),
        weatherDescription = this.weather.description
    )
}

fun convertDateTimeTxtToDayOfTheWeek(dateTime: String): String {
    val date = LocalDateTime.parse(dateTime)
    return date.dayOfWeek.name
}

fun convertDateTimeToDayOfTheWeek(unixTimestamp: Long): String {
    val localDateTime = LocalDateTime.ofInstant(
        Instant.ofEpochMilli(unixTimestamp),
        TimeZone.getDefault().toZoneId()
    )
    return localDateTime.dayOfWeek.name
}

fun convertDateTimeTxtToLocalDateTime(dateTime: String): LocalDateTime {
    return LocalDateTime.parse(dateTime)
}

fun convertDateTimeToTime(unixTimestamp: Long): String {
    val format = SimpleDateFormat("HH:mm", Locale.getDefault())
    val tz = TimeZone.getDefault()
    val now = Date()
    val offsetFromUtc = tz.getOffset(now.time)
    return format.format(unixTimestamp + offsetFromUtc)
}

