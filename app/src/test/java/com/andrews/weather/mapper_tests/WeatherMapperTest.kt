package com.andrews.weather.mapper_tests

import com.andrews.weather.mapper_tests.TestData.testCityWeatherDto
import com.andrews.weather.mapper_tests.TestData.testCityWeatherForecastDto
import com.andrews.weather.data.mappers.toCityWeatherForecast
import com.andrews.weather.data.mappers.toCurrentWeather
import com.andrews.weather.data.mappers.toTodayWeatherForecast
import com.andrews.weather.domain.WeatherIcon
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class WeatherMapperTest {

    @Test
    fun `Transformation from CityWeatherForecastDto to DailyWeather list`() {
        val resultList = testCityWeatherForecastDto.toCityWeatherForecast()
        assertThat(resultList.size).isEqualTo(4)
        assertThat(resultList[1].dayOfTheWeek).isEqualTo("WEDNESDAY")
        assertThat(resultList[0].icon).isEqualTo(WeatherIcon.PartlySunny)
        assertThat(resultList[2].humidity).isEqualTo(70)
        assertThat(resultList[3].dayTemperature).isEqualTo(4)
    }

    @Test
    fun `Transformation from CityWeatherForecastDto to Today Weather Forecast`() {
        val resultMap = testCityWeatherForecastDto.toTodayWeatherForecast()
        assertThat(resultMap.size).isEqualTo(1)
        assertThat(resultMap.keys.first()).isEqualTo("Vyshhorod")
        assertThat(resultMap["Vyshhorod"]?.dayOfTheWeek).isEqualTo("MONDAY")
        assertThat(resultMap["Vyshhorod"]?.icon).isEqualTo(WeatherIcon.Rain)
        assertThat(resultMap["Vyshhorod"]?.humidity).isEqualTo(83)
        assertThat(resultMap["Vyshhorod"]?.dayTemperature).isEqualTo(6)
    }

    @Test
    fun `Transformation from CityWeatherDto to CurrentWeather`() {
        val currentWeather = testCityWeatherDto.toCurrentWeather()

        assertThat(currentWeather.cityName).isEqualTo("Vyshhorod")
        assertThat(currentWeather.weatherDescription).isEqualTo("overcast clouds")
        assertThat(currentWeather.time.split(":")[0]).isEqualTo("18")
        assertThat(currentWeather.dayOfTheWeek).isEqualTo("MONDAY")
        assertThat(currentWeather.temperature).isEqualTo(5)
    }
}