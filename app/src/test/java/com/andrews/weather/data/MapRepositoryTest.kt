package com.andrews.weather.data

import com.andrews.weather.data.ApiTestHelper.getFileAsString
import com.andrews.weather.data.remote.OpenWeatherMapApi
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class MapRepositoryTest {

    private val mockWebServer = MockWebServer()

    private val testApiClient: OpenWeatherMapApi = Retrofit.Builder()
        .baseUrl(mockWebServer.url("/"))
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
        .create(OpenWeatherMapApi::class.java)

    private val mapRepo = MapRepositoryImpl(testApiClient)
    private val latLngBounds = LatLngBounds(
        LatLng(-113.63028388470411, 37.16353754757103),
        LatLng(-112.50026185065508, 38.984513903385874)
    )

    @After
    fun shutdown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `Failure client request returns Resource Error with 400 status code message`() {
        val response = MockResponse()
            .setResponseCode(400)

        mockWebServer.enqueue(response)

        runTest {
            assertThat(
                mapRepo.getCitiesInTheArea(
                    latLngBounds,
                    10f
                ).message
            ).isEqualTo("HTTP 400 Client Error")
        }
    }

    @Test
    fun `Successful request returns Resource data as list of CityInfo`() {
        val response = MockResponse()
            .setResponseCode(200)
            .setBody(getFileAsString("list_of_cities_response.json"))

        mockWebServer.enqueue(response)

        runTest {
            val result = mapRepo.getCitiesInTheArea(latLngBounds, 10f)
            assertThat(result.data).isNotEmpty()
            assertThat(result.data?.get(0)?.name).isEqualTo("Cedar City")
            assertThat(result.data?.get(0)?.id).isEqualTo(5536630)
            assertThat(result.data?.get(0)?.temperature).isEqualTo(7)
            assertThat(result.data?.get(0)?.time).isEqualTo("18:16")
        }
    }
}