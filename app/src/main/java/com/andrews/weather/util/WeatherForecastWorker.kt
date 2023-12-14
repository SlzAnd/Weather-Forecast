package com.andrews.weather.util

import android.annotation.SuppressLint
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.andrews.weather.domain.repository.WeatherRepository
import com.andrews.weather.domain.util.Resource
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Priority
import kotlinx.coroutines.delay

class WeatherForecastWorker(
    context: Context,
    workerParameters: WorkerParameters,
    private val fusedLocationProviderClient: FusedLocationProviderClient,
    private val weatherRepository: WeatherRepository
) : CoroutineWorker(context, workerParameters) {
    private val notificationId = 1

    @SuppressLint("MissingPermission")
    override suspend fun doWork(): Result {
        if (applicationContext.hasLocationPermission() && applicationContext.isLocationEnabled()) {
            val locationResult =
                fusedLocationProviderClient.getCurrentLocation(
                    Priority.PRIORITY_HIGH_ACCURACY,
                    null
                )

            delay(5000)

            val weatherRequest =
                weatherRepository.getWeatherForecastFromLocation(locationResult.result)

            if (weatherRequest is Resource.Success) {
                weatherRequest.data?.let {
                    val iconRes = it.values.first().icon.iconRes
                    val desc = it.values.first().description
                    val currTemp = it.values.first().dayTemperature
                    val nightTemp = it.values.first().nightTemperature
                    val message =
                        "Now is $desc, current temperature: $currTemp °C, night temperature: $nightTemp °C"

                    val builder =
                        NotificationCompat.Builder(applicationContext, "weather_forecast_channel")
                            .setSmallIcon(iconRes)
                            .setContentTitle("Weather forecast in ${it.keys.first()}:")
                            .setContentText(message)

                    with(NotificationManagerCompat.from(applicationContext)) {
                        notify(notificationId, builder.build())
                    }
                }
                return Result.success()
            } else {
                return Result.failure()
            }
        }
        return Result.failure()
    }
}