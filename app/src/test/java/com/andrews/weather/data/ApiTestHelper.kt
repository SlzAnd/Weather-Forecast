package com.andrews.weather.data

import com.andrews.weather.data.remote.OpenWeatherMapApi
import okhttp3.mockwebserver.MockWebServer
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.File

object ApiTestHelper {

    val mockWebServer = MockWebServer()

    fun getFileAsString(filePath: String): String {
        val uri = ClassLoader.getSystemResource(filePath)
        val file = File(uri.path)
        return String(file.readBytes())
    }

    val testApiClient = Retrofit.Builder()
        .baseUrl(mockWebServer.url("/"))
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
        .create(OpenWeatherMapApi::class.java)


}