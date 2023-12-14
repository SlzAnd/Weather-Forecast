package com.andrews.weather.ui

import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.andrews.weather.ui.components.BottomSheet
import com.andrews.weather.ui.map_screen.MapViewModel
import com.andrews.weather.ui.navigation.NavGraph
import com.andrews.weather.ui.navigation.Screen
import com.andrews.weather.ui.theme.MineShaft
import com.andrews.weather.ui.theme.WeatherTheme
import com.andrews.weather.ui.theme.poppinsFontFamily
import com.andrews.weather.util.ConnectivityReceiver
import com.andrews.weather.util.WeatherForecastWorker
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.concurrent.TimeUnit

class MainActivity : ComponentActivity(), ConnectivityReceiver.ConnectivityReceiverListener {

    private val mapViewModel by viewModel<MapViewModel>()
    private var isConnected by mutableStateOf(false)
    private val workManager = WorkManager.getInstance(this)

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        registerReceiver(
            ConnectivityReceiver(),
            IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        )

        val weatherNotificationsWorkRequest =
            PeriodicWorkRequestBuilder<WeatherForecastWorker>(15L, TimeUnit.MINUTES)
                .setConstraints(
                    Constraints.Builder()
                        .setRequiredNetworkType(NetworkType.CONNECTED)
                        .build()
                )
                .setInitialDelay(10L, TimeUnit.SECONDS)
                .build()

        workManager.enqueue(weatherNotificationsWorkRequest)

        setContent {
            WeatherTheme {
                val navController = rememberNavController()
                val scaffoldState = rememberBottomSheetScaffoldState(
                    bottomSheetState = SheetState(
                        skipHiddenState = false,
                        skipPartiallyExpanded = false,
                        initialValue = SheetValue.PartiallyExpanded
                    )
                )
                val scope = rememberCoroutineScope()
                val state by mapViewModel.state.collectAsState()

                scope.launch {
                    navController.currentBackStackEntryFlow.collect {
                        if (it.destination.route == Screen.MapScreen.route) {
                            scaffoldState.bottomSheetState.partialExpand()
                        }
                        if (it.destination.route == Screen.DetailCityWeather.route) {
                            scaffoldState.bottomSheetState.hide()
                        }
                    }
                }

                BottomSheetScaffold(
                    scaffoldState = scaffoldState,
                    sheetContent = {
                        BottomSheet(cities = state.cities, onItemClick = {
                            scope.launch {
                                scaffoldState.bottomSheetState.partialExpand()
                                navController.navigate(Screen.DetailCityWeather.passCityName(it))
                            }
                        })
                    },
                    sheetDragHandle = {
                        Column {
                            Spacer(modifier = Modifier.height(13.dp))
                            Box(
                                modifier = Modifier
                                    .width(141.dp)
                                    .height(7.dp)
                                    .background(
                                        color = Color.White,
                                        shape = RoundedCornerShape(29.dp)
                                    )
                            )
                        }
                    },
                    sheetShape = RectangleShape,
                    sheetContainerColor = MineShaft,
                    sheetContentColor = Color.White,
                    sheetPeekHeight = 33.dp
                ) {
                    NavGraph(
                        navController = navController,
                        mapViewModel = mapViewModel,
                    )
                }
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
    }

    override fun onDestroy() {
        super.onDestroy()
        workManager.cancelAllWork()
    }
}