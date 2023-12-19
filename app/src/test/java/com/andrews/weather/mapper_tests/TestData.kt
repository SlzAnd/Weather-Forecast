package com.andrews.weather.mapper_tests

import com.andrews.weather.data.model.CitiesDto
import com.andrews.weather.data.model.City
import com.andrews.weather.data.model.CityWeatherDto
import com.andrews.weather.data.model.CityWeatherForecastDto
import com.andrews.weather.data.model.CityX
import com.andrews.weather.data.model.Main
import com.andrews.weather.data.model.Weather
import com.andrews.weather.data.model.WeatherForecast

object TestData {

    val testCityList = listOf(
        City(
            dt = 1702909213,
            id = 5536630,
            name = "Test0 City",
            main = Main(
                temp = -4.46,
                humidity = 80
            )
        ),
        City(
            dt = 1702909213,
            id = 5536631,
            name = "Test1 City",
            main = Main(
                temp = 4.46,
                humidity = 70
            )
        ),
        City(
            dt = 1702909213,
            id = 5536632,
            name = "Test2 City",
            main = Main(
                temp = -14.46,
                humidity = 50
            )
        )
    )

    val testCitiesDto = CitiesDto(
        calctime = 0.000482736,
        cnt = 3,
        cod = 200,
        list = testCityList
    )

    val testCityWeatherDto = CityWeatherDto(
        name = "Vyshhorod",
        dt = 1702915495, // 18:05
        main = Main(temp = 5.52, humidity = 94),
        weather = listOf(
            Weather(
                description = "overcast clouds",
                icon = "04n",
                id = 804,
                main = "Clouds"
            )
        ),
        timezone = 7200
    )

    val testWeatherForecastList = listOf(
        WeatherForecast(
            dt = 1702954800,
            dt_txt = "2023-12-18 03:00:00",
            main = Main(temp = 6.2, humidity = 83),
            weather = listOf(Weather(description = "overcast clouds", icon = "04n", id = 500, main = "Clouds"))
        ),
        WeatherForecast(
            dt = 1702965600,
            dt_txt = "2023-12-18 06:00:00",
            main = Main(temp = 4.71, humidity = 95),
            weather = listOf(Weather(description = "overcast clouds", icon = "04d", id = 804, main = "Clouds"))
        ),
        WeatherForecast(
            dt = 1702976400,
            dt_txt = "2023-12-18 09:00:00",
            main = Main(temp = 5.31, humidity = 94),
            weather = listOf(Weather(description = "light rain", icon = "10d", id = 500, main = "Rain"))
        ),
        WeatherForecast(
            dt = 1702987200,
            dt_txt = "2023-12-18 12:00:00",
            main = Main(temp = 6.86, humidity = 75),
            weather = listOf(Weather(description = "overcast clouds", icon = "04d", id = 804, main = "Clouds"))
        ),
        WeatherForecast(
            dt = 1702998000,
            dt_txt = "2023-12-18 15:00:00",
            main = Main(temp = 6.9, humidity = 65),
            weather = listOf(Weather(description = "overcast clouds", icon = "04n", id = 804, main = "Clouds"))
        ),
        WeatherForecast(
            dt = 1702922400,
            dt_txt = "2023-12-18 18:00:00",
            main = Main(temp = 6.29, humidity = 83),
            weather = listOf(Weather(description = "light rain", icon = "10n", id = 500, main = "Rain"))
        ),
        WeatherForecast(
            dt = 1702933200,
            dt_txt = "2023-12-18 21:00:00",
            main = Main(temp = 5.33, humidity = 90),
            weather = listOf(Weather(description = "light rain", icon = "10n", id = 500, main = "Rain"))
        ),
        WeatherForecast(
            dt = 1702944000,
            dt_txt = "2023-12-19 00:00:00",
            main = Main(temp = 4.32, humidity = 96),
            weather = listOf(Weather(description = "light rain", icon = "10n", id = 500, main = "Rain"))
        ),
        WeatherForecast(
            dt = 1702954800,
            dt_txt = "2023-12-19 03:00:00",
            main = Main(temp = 6.2, humidity = 83),
            weather = listOf(Weather(description = "overcast clouds", icon = "04n", id = 500, main = "Clouds"))
        ),
        WeatherForecast(
            dt = 1702965600,
            dt_txt = "2023-12-19 06:00:00",
            main = Main(temp = 4.71, humidity = 95),
            weather = listOf(Weather(description = "overcast clouds", icon = "04d", id = 804, main = "Clouds"))
        ),
        WeatherForecast(
            dt = 1702976400,
            dt_txt = "2023-12-19 09:00:00",
            main = Main(temp = 5.31, humidity = 94),
            weather = listOf(Weather(description = "light rain", icon = "10d", id = 500, main = "Rain"))
        ),
        WeatherForecast(
            dt = 1702987200,
            dt_txt = "2023-12-19 12:00:00",
            main = Main(temp = 6.86, humidity = 75),
            weather = listOf(Weather(description = "overcast clouds", icon = "04d", id = 804, main = "Clouds"))
        ),
        WeatherForecast(
            dt = 1702998000,
            dt_txt = "2023-12-19 15:00:00",
            main = Main(temp = 6.9, humidity = 65),
            weather = listOf(Weather(description = "overcast clouds", icon = "04n", id = 804, main = "Clouds"))
        ),
        WeatherForecast(
            dt = 1703008800,
            dt_txt = "2023-12-19 18:00:00",
            main = Main(temp = 6.47, humidity = 72),
            weather = listOf(Weather(description = "overcast clouds", icon = "04n", id = 804, main = "Clouds"))
        ),
        WeatherForecast(
            dt = 1703019600,
            dt_txt = "2023-12-19 21:00:00",
            main = Main(temp = 6.28, humidity = 76),
            weather = listOf(Weather(description = "overcast clouds", icon = "04n", id = 804, main = "Clouds"))
        ),
        WeatherForecast(
            dt = 1703030400,
            dt_txt = "2023-12-20 00:00:00",
            main = Main(temp = 4.32, humidity = 63),
            weather = listOf(Weather(description = "light rain", icon = "10n", id = 500, main = "Rain"))
        ),
        WeatherForecast(
            dt = 1703041200,
            dt_txt = "2023-12-20 03:00:00",
            main = Main(temp = 6.78, humidity = 83),
            weather = listOf(Weather(description = "overcast clouds", icon = "04n", id = 500, main = "Clouds"))
        ),
        WeatherForecast(
            dt = 1703052000,
            dt_txt = "2023-12-20 06:00:00",
            main = Main(temp = 5.95, humidity = 63),
            weather = listOf(Weather(description = "overcast clouds", icon = "04d", id = 804, main = "Clouds"))
        ),
        WeatherForecast(
            dt = 1703062800,
            dt_txt = "2023-12-20 09:00:00",
            main = Main(temp = 6.06, humidity = 63),
            weather = listOf(Weather(description = "overcast clouds", icon = "04d", id = 804, main = "Clouds"))
        ),
        WeatherForecast(
            dt = 1703073600,
            dt_txt = "2023-12-20 12:00:00",
            main = Main(temp = 6.29, humidity = 58),
            weather = listOf(Weather(description = "broken clouds", icon = "04d", id = 803, main = "Clouds"))
        ),
        WeatherForecast(
            dt = 1703084400,
            dt_txt = "2023-12-20 15:00:00",
            main = Main(temp = 5.7, humidity = 67),
            weather = listOf(Weather(description = "scattered clouds", icon = "03n", id = 802, main = "Clouds"))
        ),
        WeatherForecast(
            dt = 1703095200,
            dt_txt = "2023-12-20 18:00:00",
            main = Main(temp = 5.15, humidity = 70),
            weather = listOf(Weather(description = "broken clouds", icon = "04n", id = 803, main = "Clouds"))
        ),
        WeatherForecast(
            dt = 1703106000,
            dt_txt = "2023-12-20 21:00:00",
            main = Main(temp = 4.46, humidity = 75),
            weather = listOf(Weather(description = "overcast clouds", icon = "04n", id = 804, main = "Clouds"))
        ),
        WeatherForecast(
            dt = 1703116800,
            dt_txt = "2023-12-21 00:00:00",
            main = Main(temp = 4.3, humidity = 90),
            weather = listOf(Weather(description = "overcast clouds", icon = "04n", id = 804, main = "Clouds"))
        ),
        WeatherForecast(
            dt = 1703127600,
            dt_txt = "2023-12-21 03:00:00",
            main = Main(temp = 6.2, humidity = 83),
            weather = listOf(Weather(description = "overcast clouds", icon = "04n", id = 500, main = "Clouds"))
        ),
        WeatherForecast(
            dt = 1703138400,
            dt_txt = "2023-12-21 06:00:00",
            main = Main(temp = 3.97, humidity = 68),
            weather = listOf(Weather(description = "overcast clouds", icon = "04d", id = 804, main = "Clouds"))
        ),
        WeatherForecast(
            dt = 1703149200,
            dt_txt = "2023-12-21 09:00:00",
            main = Main(temp = 4.69, humidity = 64),
            weather = listOf(Weather(description = "overcast clouds", icon = "04d", id = 804, main = "Clouds"))
        ),
        WeatherForecast(
            dt = 1703160000,
            dt_txt = "2023-12-21 12:00:00",
            main = Main(temp = 6.29, humidity = 70),
            weather = listOf(Weather(description = "overcast clouds", icon = "04d", id = 804, main = "Clouds"))
        ), // copies starts here
        WeatherForecast(
            dt = 1703160000,
            dt_txt = "2023-12-21 15:00:00",
            main = Main(temp = 4.49, humidity = 70),
            weather = listOf(Weather(description = "overcast clouds", icon = "04d", id = 804, main = "Clouds"))
        ),
        WeatherForecast(
            dt = 1703160000,
            dt_txt = "2023-12-21 18:00:00",
            main = Main(temp = 4.49, humidity = 70),
            weather = listOf(Weather(description = "overcast clouds", icon = "04d", id = 804, main = "Clouds"))
        ),
        WeatherForecast(
            dt = 1703160000,
            dt_txt = "2023-12-21 21:00:00",
            main = Main(temp = 4.49, humidity = 70),
            weather = listOf(Weather(description = "overcast clouds", icon = "04d", id = 804, main = "Clouds"))
        ),
        WeatherForecast(
            dt = 1703160000,
            dt_txt = "2023-12-22 00:00:00",
            main = Main(temp = 4.49, humidity = 70),
            weather = listOf(Weather(description = "overcast clouds", icon = "04d", id = 804, main = "Clouds"))
        ),
        WeatherForecast(
            dt = 1703127600,
            dt_txt = "2023-12-22 03:00:00",
            main = Main(temp = 6.2, humidity = 83),
            weather = listOf(Weather(description = "overcast clouds", icon = "04n", id = 500, main = "Clouds"))
        ),
        WeatherForecast(
            dt = 1703138400,
            dt_txt = "2023-12-22 06:00:00",
            main = Main(temp = 3.97, humidity = 68),
            weather = listOf(Weather(description = "overcast clouds", icon = "04d", id = 804, main = "Clouds"))
        ),
        WeatherForecast(
            dt = 1703149200,
            dt_txt = "2023-12-22 09:00:00",
            main = Main(temp = 4.69, humidity = 64),
            weather = listOf(Weather(description = "overcast clouds", icon = "04d", id = 804, main = "Clouds"))
        ),
        WeatherForecast(
            dt = 1703160000,
            dt_txt = "2023-12-22 12:00:00",
            main = Main(temp = 6.29, humidity = 70),
            weather = listOf(Weather(description = "overcast clouds", icon = "04d", id = 804, main = "Clouds"))
        ),
        WeatherForecast(
            dt = 1703160000,
            dt_txt = "2023-12-22 15:00:00",
            main = Main(temp = 4.49, humidity = 70),
            weather = listOf(Weather(description = "overcast clouds", icon = "04d", id = 804, main = "Clouds"))
        ),
        WeatherForecast(
            dt = 1703160000,
            dt_txt = "2023-12-22 18:00:00",
            main = Main(temp = 4.49, humidity = 70),
            weather = listOf(Weather(description = "overcast clouds", icon = "04d", id = 804, main = "Clouds"))
        ),
        WeatherForecast(
            dt = 1703278800,
            dt_txt = "2023-12-22 21:00:00",
            main = Main(temp = 4.49, humidity = 70),
            weather = listOf(Weather(description = "overcast clouds", icon = "04d", id = 804, main = "Clouds"))
        ),
        WeatherForecast(
            dt = 1703289600,
            dt_txt = "2023-12-23 00:00:00",
            main = Main(temp = 4.49, humidity = 70),
            weather = listOf(Weather(description = "overcast clouds", icon = "04d", id = 804, main = "Clouds"))
        ),
        WeatherForecast(
            dt = 1703300400,
            dt_txt = "2023-12-23 03:00:00",
            main = Main(temp = 6.2, humidity = 83),
            weather = listOf(Weather(description = "overcast clouds", icon = "04d", id = 500, main = "Clouds"))
        ),
        WeatherForecast(
            dt = 1703311200,
            dt_txt = "2023-12-23 06:00:00",
            main = Main(temp = 4.49, humidity = 70),
            weather = listOf(Weather(description = "overcast clouds", icon = "04d", id = 804, main = "Clouds"))
        ),
        WeatherForecast(
            dt = 1703322000,
            dt_txt = "2023-12-23 09:00:00",
            main = Main(temp = 4.49, humidity = 70),
            weather = listOf(Weather(description = "overcast clouds", icon = "04d", id = 804, main = "Clouds"))
        ),
        WeatherForecast(
            dt = 1703332800,
            dt_txt = "2023-12-23 12:00:00",
            main = Main(temp = 6.29, humidity = 70),
            weather = listOf(Weather(description = "overcast clouds", icon = "04d", id = 804, main = "Clouds"))
        ),
        WeatherForecast(
            dt = 1703160000,
            dt_txt = "2023-12-23 15:00:00",
            main = Main(temp = 4.49, humidity = 70),
            weather = listOf(Weather(description = "overcast clouds", icon = "04d", id = 804, main = "Clouds"))
        ),
        WeatherForecast(
            dt = 1703160000,
            dt_txt = "2023-12-23 18:00:00",
            main = Main(temp = 4.49, humidity = 70),
            weather = listOf(Weather(description = "overcast clouds", icon = "04d", id = 804, main = "Clouds"))
        ),
        WeatherForecast(
            dt = 1703278800,
            dt_txt = "2023-12-23 21:00:00",
            main = Main(temp = 4.49, humidity = 70),
            weather = listOf(Weather(description = "overcast clouds", icon = "04d", id = 804, main = "Clouds"))
        ),
        WeatherForecast(
            dt = 1703289600,
            dt_txt = "2023-12-24 00:00:00",
            main = Main(temp = 4.49, humidity = 70),
            weather = listOf(Weather(description = "overcast clouds", icon = "04d", id = 804, main = "Clouds"))
        ),
    )

    val testCityWeatherForecastDto = CityWeatherForecastDto(
        city = CityX(id = 688723, name = "Vyshhorod", timezone = 7200),
        list = testWeatherForecastList
    )
}