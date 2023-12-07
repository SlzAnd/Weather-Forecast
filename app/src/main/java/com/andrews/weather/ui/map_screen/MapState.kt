package com.andrews.weather.ui.map_screen

import android.location.Location
import com.andrews.weather.domain.model.CityInfo

data class MapState(
    val lastKnownLocation: Location? = null,
    val cities: List<CityInfo> = emptyList(),
    val isConnected: Boolean = true,
    val isError: Boolean = false,
    val errorMessage: String = "Error message"
)
