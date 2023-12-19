package com.andrews.weather.data

import android.location.Location
import com.andrews.weather.data.remote.OpenWeatherMapApi
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertThrows
import org.junit.Test
import org.mockito.Mockito.mock
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


class WeatherRepositoryTest {
    private val mockWebServer = MockWebServer()

    private val testApiClient: OpenWeatherMapApi = Retrofit.Builder()
        .baseUrl(mockWebServer.url("/"))
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
        .create(OpenWeatherMapApi::class.java)

    private val weatherRepo = WeatherRepositoryImpl(testApiClient)

    private val testLocation: Location = mock()

    @After
    fun shutdown() {
        mockWebServer.shutdown()
    }

    // getWeather tests
    @Test
    fun `Incorrect city name in getWeather request returns Client Error with 404 status code message`() {
        val response = MockResponse()
            .setResponseCode(404)

        mockWebServer.enqueue(response)

        val e = assertThrows(retrofit2.HttpException::class.java) {
            runTest {
                weatherRepo.getWeather(
                    "Vyshhorodsdfsdf"
                )
            }
        }
        assertThat(e.message).isEqualTo("HTTP 404 Client Error")
    }

    @Test
    fun `Correct city name in getWeather request returns CurrentWeather object`() {
        val response = MockResponse()
            .setResponseCode(200)
            .setBody(ApiTestHelper.getFileAsString("weather_response_Vyshhorod.json"))

        mockWebServer.enqueue(response)

        runTest {
            val currentWeather = weatherRepo.getWeather("Vyshhorod")
            assertThat(currentWeather).isNotNull()
            assertThat(currentWeather.cityName).isEqualTo("Vyshhorod")
            assertThat(currentWeather.temperature).isEqualTo(6)
        }
    }

    // getWeatherForecast tests
    @Test
    fun `Incorrect city name in getWeatherForecast returns Client Error with 404 status code message`() {
        val response = MockResponse()
            .setResponseCode(404)

        mockWebServer.enqueue(response)

        val e = assertThrows(retrofit2.HttpException::class.java) {
            runTest {
                weatherRepo.getWeatherForecast(
                    "Vyshhorodsdfsdf"
                )
            }
        }
        assertThat(e.message).isEqualTo("HTTP 404 Client Error")
    }

    @Test
    fun `Correct city name in getWeatherForecast request returns not empty List of DailyWeather`() {
        val response = MockResponse()
            .setResponseCode(200)
            .setBody(ApiTestHelper.getFileAsString("forecast_weather.json"))

        mockWebServer.enqueue(response)

        runTest {
            val resultList = weatherRepo.getWeatherForecast("Vyshhorod")
            assertThat(resultList).isNotEmpty()
            assertThat(resultList.size).isEqualTo(4) // 4 days forecast

        }
    }


    // getWeatherForecastFromLocation tests
    @Test
    fun `Incorrect location in getWeatherForecastFromLocation returns Resource Error with 404 status code message`() {
        val response = MockResponse()
            .setResponseCode(404)

        mockWebServer.enqueue(response)

        runTest {
            val result = weatherRepo.getWeatherForecastFromLocation(testLocation)
            assertThat(result.data).isNull()
            assertThat(result.message).isEqualTo("HTTP 404 Client Error")
        }
    }

    @Test
    fun `Correct location in getWeatherForecastFromLocation request returns not empty List of DailyWeather`() {
        val response = MockResponse()
            .setResponseCode(200)
            .setBody(ApiTestHelper.getFileAsString("forecast_weather.json"))

        mockWebServer.enqueue(response)

        runTest {
            val resultMap = weatherRepo.getWeatherForecastFromLocation(testLocation)
            assertThat(resultMap.data).isNotEmpty()
            assertThat(resultMap.message).isNull()
            assertThat(resultMap.data?.keys?.first()).isEqualTo("Vyshhorod")
        }
    }
}