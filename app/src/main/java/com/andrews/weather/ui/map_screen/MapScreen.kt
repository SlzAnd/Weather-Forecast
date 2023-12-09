package com.andrews.weather.ui.map_screen

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.andrews.weather.R
import com.andrews.weather.ui.components.ErrorScreen
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

private val DEFAULT_LOCATION_LAT_LNG = LatLng(50.45466, 30.5238); // Kyiv location

@OptIn(
    MapsComposeExperimentalApi::class, ExperimentalPermissionsApi::class,
)
@Composable
fun MapScreen(
    viewModel: MapViewModel,
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var dialog: AlertDialog?
    val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

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
        DEFAULT_LOCATION_LAT_LNG
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
                context.resources.getString(R.string.permission_denied_forever),
                Toast.LENGTH_LONG
            ).show()
        } else {
            dialog = AlertDialog.Builder(context)
                .setTitle(context.resources.getString(R.string.dialog_title))
                .setMessage(context.resources.getString(R.string.dialog_text).trimIndent())
                .setPositiveButton(
                    context.resources.getString(R.string.dialog_button_text)
                ) { _, _ ->
                    context.startActivity(settingsIntent)
                }
                .create()
            dialog!!.show()
        }
    }

    if (state.isConnected) {
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
                        text = stringResource(id = R.string.unknown_location_message),
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
                            text = stringResource(id = R.string.unknown_location_button_text),
                            style = TextStyle(
                                fontSize = 14.sp,
                                fontFamily = poppinsFontFamily,
                                fontWeight = FontWeight(500),
                            )
                        )
                    }
                }
            }

            if (state.isError) {
                ErrorScreen(errorMessage = state.errorMessage)
            }
        }
    }
}