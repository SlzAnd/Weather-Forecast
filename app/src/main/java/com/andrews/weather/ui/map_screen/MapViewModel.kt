package com.andrews.weather.ui.map_screen

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andrews.weather.domain.repository.MapRepository
import com.andrews.weather.domain.util.Resource
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.model.LatLngBounds
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MapViewModel(
    private val repository: MapRepository
) : ViewModel() {

    private val _state = MutableStateFlow(MapState())
    val state = _state.asStateFlow()

    @SuppressLint("MissingPermission")
    fun getDeviceLocation(
        fusedLocationProviderClient: FusedLocationProviderClient
    ) {
        val locationResult = fusedLocationProviderClient.getCurrentLocation(
            Priority.PRIORITY_HIGH_ACCURACY,
            null
        )
        locationResult.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                _state.value = _state.value.copy(
                    lastKnownLocation = task.result,
                )
            }
        }
    }

    fun updateBounds(updatedBounds: LatLngBounds, zoom: Float) {
        viewModelScope.launch(Dispatchers.IO) {
            if (state.value.isConnected) {
                val result = repository.getCitiesInTheArea(updatedBounds, zoom)
                when (result) {
                    is Resource.Error -> {
                        _state.value = _state.value.copy(
                            isError = true,
                            errorMessage = result.message ?: "Request was failed!"
                        )
                    }

                    is Resource.Success -> {
                        _state.value = _state.value.copy(
                            isError = false,
                            cities = result.data ?: emptyList()
                        )
                    }
                }
            }
        }
    }

    fun changeConnectionStatus(isConnected: Boolean) {
        _state.value = _state.value.copy(
            isConnected = isConnected
        )
    }
}