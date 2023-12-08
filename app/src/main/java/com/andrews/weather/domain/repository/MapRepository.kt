package com.andrews.weather.domain.repository

import com.andrews.weather.domain.model.CityInfo
import com.andrews.weather.domain.util.Resource
import com.google.android.gms.maps.model.LatLngBounds

interface MapRepository {
    suspend fun getCitiesInTheArea(
        updatedBounds: LatLngBounds,
        zoom: Float
    ): Resource<List<CityInfo>>
}