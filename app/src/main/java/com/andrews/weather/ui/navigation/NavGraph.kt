package com.andrews.weather.ui.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.andrews.weather.ui.weather_screen.DetailCityWeatherScreen
import com.andrews.weather.ui.map_screen.MapScreen
import com.andrews.weather.ui.map_screen.MapViewModel
import com.andrews.weather.ui.weather_screen.WeatherViewModel

@Composable
fun NavGraph(
    navController: NavHostController,
    mapViewModel: MapViewModel,
    weatherViewModel: WeatherViewModel,
    context: Context
) {
    NavHost(navController = navController, startDestination = Screen.MapScreen.route) {
        composable(Screen.MapScreen.route) {
            MapScreen(
                navController = navController,
                viewModel = mapViewModel,
                context = context)
        }

        composable(
            route = Screen.DetailCityWeather.route,
            arguments = listOf(
                navArgument("cityName") {
                    type = NavType.StringType
                }
            )
        ) {
            DetailCityWeatherScreen(
                viewModel = weatherViewModel
            )
        }
    }
}