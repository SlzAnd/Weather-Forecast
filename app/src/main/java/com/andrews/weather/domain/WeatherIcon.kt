package com.andrews.weather.domain

import androidx.annotation.DrawableRes
import com.andrews.weather.R

sealed class WeatherIcon(
    @DrawableRes val iconRes: Int
) {
    object ClearSky : WeatherIcon(
        iconRes = R.drawable.ic_sunny
    )

    object Thunderstorm : WeatherIcon(
        iconRes = R.drawable.ic_thunderstorm
    )

    object PartlySunny : WeatherIcon(
        iconRes = R.drawable.ic_partly_sunny
    )

    object Rain : WeatherIcon(
        iconRes = R.drawable.ic_rainy
    )

    object Snow : WeatherIcon(
        iconRes = R.drawable.snowflake
    )

    object Mist : WeatherIcon(
        iconRes = R.drawable.ic_fog
    )

    object Drizzle : WeatherIcon(
        iconRes = R.drawable.drizzle
    )


    companion object {
        fun fromIconCode(code: Int): WeatherIcon {
            return when (code) {
                in 200..232 -> Thunderstorm
                in 300..321 -> Drizzle
                in 500..531 -> Rain
                in 600..622 -> Snow
                in 701..781 -> Mist
                in 801..804 -> PartlySunny
                800 -> ClearSky
                else -> ClearSky
            }
        }
    }
}