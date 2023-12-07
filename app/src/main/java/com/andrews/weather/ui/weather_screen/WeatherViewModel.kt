package com.andrews.weather.ui.weather_screen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andrews.weather.domain.repository.WeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class WeatherViewModel(
    repository: WeatherRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _state = MutableStateFlow(WeatherState())
    val state = _state.asStateFlow()

    init {
        savedStateHandle.get<String>("cityName")?.let { cityName ->
            viewModelScope.launch(Dispatchers.IO) {
                _state.value = _state.value.copy(
                    cityName = cityName,
                    currentWeather = repository.getWeather(cityName),
                    dailyWeatherList = repository.getWeatherForecast(cityName)
                )
            }
        }
    }

    fun changeConnectionStatus(isConnected: Boolean) {
        _state.value = _state.value.copy(
            isConnected = isConnected
        )
    }
}