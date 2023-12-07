package com.andrews.weather.ui

import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.andrews.weather.ui.map_screen.MapScreen
import com.andrews.weather.ui.map_screen.MapViewModel
import com.andrews.weather.ui.navigation.NavGraph
import com.andrews.weather.ui.theme.WeatherTheme
import com.andrews.weather.ui.theme.poppinsFontFamily
import com.andrews.weather.ui.weather_screen.WeatherViewModel
import com.andrews.weather.util.ConnectivityReceiver
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity(), ConnectivityReceiver.ConnectivityReceiverListener {

    private val mapViewModel by viewModel<MapViewModel>()
    private val weatherViewModel by viewModel<WeatherViewModel>()
    private var isConnected by mutableStateOf(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        registerReceiver(
            ConnectivityReceiver(),
            IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        )

        setContent {
            WeatherTheme {
                val navController = rememberNavController()
                MapScreen(navController, mapViewModel, this)
//                NavGraph(
//                    navController = navController,
//                    mapViewModel = mapViewModel,
//                    weatherViewModel = weatherViewModel,
//                    this
//                )
                if (!isConnected) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.BottomCenter
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.Red),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "You are offline!",
                                fontSize = 16.sp,
                                fontFamily = poppinsFontFamily,
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        ConnectivityReceiver.connectivityReceiverListener = this
    }

    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        this.isConnected = isConnected
        mapViewModel.changeConnectionStatus(isConnected)
        weatherViewModel.changeConnectionStatus(isConnected)
    }
}