package com.andrews.weather.util

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.andrews.weather.R
import com.andrews.weather.domain.repository.WeatherRepository
import com.andrews.weather.domain.util.Resource
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Priority
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import org.koin.android.ext.android.get

@SuppressLint("MissingPermission")
class WeatherForecastNotificationService : Service() {

    private val notificationId = 123
    private val delayTime = 1 // Delay for 1 minute
    private val serviceScope = CoroutineScope(Dispatchers.IO)
    private val fusedLocationProviderClient: FusedLocationProviderClient = get()
    private val weatherRepository: WeatherRepository = get()

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startForeground(notificationId, createNotification())

        // Start a coroutine for fetching weather and showing notifications every $delayTime
        serviceScope.launch {
            while (isActive) {
                fetchWeatherAndShowNotification()
                delay(delayTime.toLong() * 60 * 1000 - 5000) // delay in minutes - 5 second(delay for getting location in the fetchWeatherAndShowNotification function)
            }
        }

        return START_NOT_STICKY
    }

    private fun createNotification(): Notification {
        return NotificationCompat.Builder(applicationContext, "weather_forecast_channel")
            .setSmallIcon(R.drawable.ic_shovel)
            .setContentTitle("Weather forecast")
            .setContentText("Fetching data...")
            .build()
    }

    private suspend fun fetchWeatherAndShowNotification() {
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

                    showNotification(builder.build())
                }
            }
        }
    }

    private fun showNotification(notification: Notification) {
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(notificationId, notification)
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}
