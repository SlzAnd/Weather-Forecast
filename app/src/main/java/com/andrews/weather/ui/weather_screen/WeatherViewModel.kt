package com.andrews.weather.ui.weather_screen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andrews.weather.domain.repository.WeatherRepository
import com.andrews.weather.util.ConstValues.NAV_KEY_CITY_NAME
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class WeatherViewModel(
    repository: WeatherRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow(WeatherState())
    val state = _state.asStateFlow()

    init {
        savedStateHandle.get<String>(NAV_KEY_CITY_NAME)?.let { cityName ->
            viewModelScope.launch(Dispatchers.IO) {
                _state.value = _state.value.copy(
                    cityName = cityName,
                    dailyWeatherList = repository.getWeatherForecast(cityName),
                    currentWeather = repository.getWeather(cityName)
                )
            }
        }
    }
}