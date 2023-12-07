package com.andrews.weather.di

import com.andrews.weather.data.MapRepositoryImpl
import com.andrews.weather.data.WeatherRepositoryImpl
import com.andrews.weather.data.remote.OpenWeatherMapApi
import com.andrews.weather.domain.repository.MapRepository
import com.andrews.weather.domain.repository.WeatherRepository
import com.andrews.weather.ui.map_screen.MapViewModel
import com.andrews.weather.ui.weather_screen.WeatherViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val appModule = module {

    single {
        Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(OpenWeatherMapApi::class.java)
    }

    single<MapRepository> {
        MapRepositoryImpl(get())
    }

    viewModel {
        MapViewModel(get())
    }

    single<WeatherRepository> {
        WeatherRepositoryImpl(get())
    }

    viewModel {
        WeatherViewModel(get(), get())
    }
}