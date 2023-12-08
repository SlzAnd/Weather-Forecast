package com.andrews.weather.data.mappers

import com.andrews.weather.data.model.CityWeatherDto
import com.andrews.weather.data.model.CityWeatherForecastDto
import com.andrews.weather.data.model.WeatherForecast
import com.andrews.weather.domain.WeatherIcon
import com.andrews.weather.domain.model.CurrentWeather
import com.andrews.weather.domain.model.DailyWeather
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter


fun CityWeatherForecastDto.toCityWeatherForecast(): List<DailyWeather> {
    val map = buildMap {
        this[0] = emptyList<WeatherForecast>().toMutableList()
        this[1] = emptyList<WeatherForecast>().toMutableList()
        this[2] = emptyList<WeatherForecast>().toMutableList()
        this[3] = emptyList<WeatherForecast>().toMutableList()
    }

    return list
        .filter { convertDateTimeTxtToLocalDateTime(it.dt_txt).dayOfYear != LocalDateTime.now().dayOfYear } // remove current day
        .mapIndexed { index, forecast ->
            // grouping forecast by days, we take 4 days starts from tomorrow.
            // 5's day excluded(because it has not full forecast)
            // our list contains forecast for each 3 hour. So full day contains 7 time points.
            // We start from 1 index it's 03:00 for getting 00:00 as last point
            when (index) {
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
        }.map { dayMap ->
            val resultList = mutableListOf<DailyWeather>()
            dayMap.forEach { (_, list) ->
                resultList.add(
                    DailyWeather(
                        dayOfTheWeek = convertDateTimeTxtToDayOfTheWeek(list[1].dt_txt),
                        icon = WeatherIcon.fromIconCode(list[3].weather[0].id), // weather icon 12:00
                        humidity = list[3].main.humidity, // humidity 12:00
                        dayTemperature = list[3].main.temp.toInt(), // day temperature 12:00
                        nightTemperature = list[7].main.temp.toInt(), // night temperature 00:00
                    )
                )
            }
            return resultList
        }
}

fun CityWeatherDto.toCurrentWeather(): CurrentWeather {
    return CurrentWeather(
        cityName = this.name,
        dayOfTheWeek = convertDateTimeToDayOfTheWeek(this.dt.toLong(), this.timezone),
        temperature = this.main.temp.toInt(),
        time = convertDateTimeToTime(this.dt.toLong(), this.timezone),
        weatherDescription = this.weather[0].description
    )
}

fun convertDateTimeTxtToDayOfTheWeek(dateTime: String): String {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    val date = LocalDateTime.parse(dateTime, formatter)
    return date.dayOfWeek.name
}

fun convertDateTimeToDayOfTheWeek(unixTimestamp: Long, timeZone: Int): String {
    val offset = ZoneOffset.ofTotalSeconds(timeZone)
    val localDateTime = LocalDateTime.ofInstant(
        Instant.ofEpochMilli(unixTimestamp * 1000),
        offset
    )
    return localDateTime.dayOfWeek.name
}

fun convertDateTimeTxtToLocalDateTime(dateTime: String): LocalDateTime {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    return LocalDateTime.parse(dateTime, formatter)
}

fun convertDateTimeToTime(timestamp: Long, timezone: Int): String {
    val instant = Instant.ofEpochMilli(timestamp * 1000)
    val zoneId = ZoneId.ofOffset("GMT", ZoneOffset.ofTotalSeconds(timezone))
    val zonedDateTime = instant.atZone(zoneId)
    val formatter = DateTimeFormatter.ofPattern("HH:mm")
    return zonedDateTime.format(formatter)
}

