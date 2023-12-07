package com.andrews.weather.ui.map_screen

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavHostController
import com.andrews.weather.ui.components.BottomSheet
import com.andrews.weather.ui.components.ErrorScreen
import com.andrews.weather.ui.navigation.Screen
import com.andrews.weather.ui.theme.MineShaft
import com.andrews.weather.ui.theme.MineShaft77
import com.andrews.weather.ui.theme.poppinsFontFamily
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapEffect
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.MapsComposeExperimentalApi
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(
    MapsComposeExperimentalApi::class, ExperimentalPermissionsApi::class,
    ExperimentalMaterial3Api::class
)
@Composable
fun MapScreen(
    navController: NavHostController,
    viewModel: MapViewModel,
    context: Context
) {
    val state by viewModel.state.collectAsState()
    val scope = rememberCoroutineScope()
    var dialog: AlertDialog?
    val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

    val scaffoldState = rememberBottomSheetScaffoldState()
    var isShown by rememberSaveable {
        mutableStateOf(false)
    }

    val permissionState =
        rememberPermissionState(permission = Manifest.permission.ACCESS_FINE_LOCATION)
    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_START) {
                permissionState.launchPermissionRequest()
            }

            if (event == Lifecycle.Event.ON_RESUME) {
                if (permissionState.status.isGranted) {
                    viewModel.getDeviceLocation(fusedLocationProviderClient)
                }
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    val mapUiSettings = MapUiSettings(
        zoomControlsEnabled = true
    )
    val mapProperties = MapProperties(
        isMyLocationEnabled = state.lastKnownLocation != null,
    )

    val currentLocation = if (state.lastKnownLocation != null) {
        LatLng(state.lastKnownLocation!!.latitude, state.lastKnownLocation!!.longitude)
    } else {
        LatLng(50.45466, 30.5238)
    }

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(currentLocation, 10f)
    }

    fun askUserForOpeningAppSettings() {
        val settingsIntent = Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.fromParts("package", context.packageName, null)
        )

        if (context.packageManager.resolveActivity(
                settingsIntent,
                PackageManager.MATCH_DEFAULT_ONLY
            ) == null
        ) {
            Toast.makeText(
                context,
                "Permission are denied forever, please check your settings!",
                Toast.LENGTH_LONG
            ).show()
        } else {
            dialog = AlertDialog.Builder(context)
                .setTitle("Sorry, we can't show the weather in your location!")
                .setMessage(
                    """
                            You have denied location permission forever.
                            But we need it to show the weather in your current location.
                            Be sure, we don't send your location to other companies or people!
                            You always can change your decision in app settings.


                            Would you like to open the app settings?

                            """
                        .trimIndent()
                )
                .setPositiveButton(
                    "Open"
                ) { _, _ ->
                    context.startActivity(settingsIntent)
                }
                .create()
            dialog!!.show()
        }
    }

    if (state.isConnected) {
        BottomSheetScaffold(
            scaffoldState = scaffoldState,
            sheetContent = {
                BottomSheet(cities = state.cities, onItemClick = {
//                    navController.navigate(Screen.DetailCityWeather.passCityName(it))
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
            Box(modifier = Modifier.fillMaxSize()) {
                GoogleMap(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 33.dp),
                    properties = mapProperties,
                    uiSettings = mapUiSettings,
                    cameraPositionState = cameraPositionState
                ) {
                    MapEffect(state.lastKnownLocation) { map ->
                        map.setOnMapLoadedCallback {
                            scope.launch {
                                cameraPositionState.animate(
                                    update = CameraUpdateFactory.newLatLng(currentLocation)
                                )
                            }
                        }
                    }

                    MapEffect(cameraPositionState.position) { map ->
                        delay(1000)
                        viewModel.updateBounds(
                            map.projection.visibleRegion.latLngBounds,
                            map.cameraPosition.zoom
                        )
                    }
                }

                if (!permissionState.status.isGranted) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .background(MineShaft77),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Sorry, we don't know your location, ",
                            style = TextStyle(
                                fontSize = 14.sp,
                                fontFamily = poppinsFontFamily,
                                fontWeight = FontWeight(500),
                                color = Color.White
                            )
                        )
                        TextButton(
                            onClick = {
                                askUserForOpeningAppSettings()
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MineShaft,
                                contentColor = Color.White
                            )
                        ) {
                            Text(
                                text = "more information",
                                style = TextStyle(
                                    fontSize = 14.sp,
                                    fontFamily = poppinsFontFamily,
                                    fontWeight = FontWeight(500),
                                )
                            )
                        }
                    }
                }
            }
        }

        if (state.isError) {
            ErrorScreen(errorMessage = state.errorMessage)
        }
    }
}