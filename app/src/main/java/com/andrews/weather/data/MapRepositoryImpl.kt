package com.andrews.weather.data

import com.andrews.weather.data.mappers.toCityInfoList
import com.andrews.weather.data.remote.OpenWeatherMapApi
import com.andrews.weather.domain.model.CityInfo
import com.andrews.weather.domain.repository.MapRepository
import com.andrews.weather.domain.util.Resource
import com.andrews.weather.util.ConstValues.MAX_ALLOWED_AREA
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import kotlin.math.sqrt

class MapRepositoryImpl(
    private val api: OpenWeatherMapApi
): MapRepository {

    override suspend fun getCitiesInTheArea(updatedBounds: LatLngBounds, zoom: Float): Resource<List<CityInfo>> {
        val boundingBox = convertLatLngBoundsToBoundingBox(updatedBounds, zoom)

        return try {
            val list = api.getListOfCities(boundingBox).toCityInfoList()
            Resource.Success(data = list)
        }catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(message = e.message ?: "API request was failed")
        }
    }

    private fun convertLatLngBoundsToBoundingBox(latLngBounds: LatLngBounds, zoom: Float): String {
        val originalArea = calculateArea(latLngBounds)

        if (originalArea > MAX_ALLOWED_AREA) {
            val centerPoint = latLngBounds.center
            return createBoundingBox(centerPoint, zoom)
        }

        return convertToBoundingBoxFormat(latLngBounds, zoom)
    }

    private fun convertToBoundingBoxFormat(latLngBounds: LatLngBounds, zoom: Float): String {
        val lonLeft = latLngBounds.southwest.longitude
        val latBottom = latLngBounds.southwest.latitude
        val lonRight = latLngBounds.northeast.longitude
        val latTop = latLngBounds.northeast.latitude

        val zoomLevel = zoom.toInt()

        return "${lonLeft},${latBottom},${lonRight},${latTop},${zoomLevel}"
    }

    private fun calculateArea(bounds: LatLngBounds): Double {
        val latDistance = bounds.northeast.latitude - bounds.southwest.latitude
        val lngDistance = bounds.northeast.longitude - bounds.southwest.longitude
        return latDistance * lngDistance
    }

    private fun createBoundingBox(centerPoint: LatLng, zoom: Float): String {
        val zoomLevel = zoom.toInt()
        val centerLat = centerPoint.latitude
        val centerLng = centerPoint.longitude
        val deltaValue = sqrt(MAX_ALLOWED_AREA) / 2

        // create bounding box with size MAX_ALLOWED_AREA(for now = 25) around the center point
        val minLng = centerLng - deltaValue
        val minLat = centerLat - deltaValue
        val maxLng = centerLng + deltaValue
        val maxLat = centerLat + deltaValue

        return "${minLng},${minLat},${maxLng},${maxLat},${zoomLevel}"
    }
}
