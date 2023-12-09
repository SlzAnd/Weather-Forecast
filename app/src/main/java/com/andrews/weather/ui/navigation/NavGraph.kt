package com.andrews.weather.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.andrews.weather.ui.weather_screen.DetailCityWeatherScreen
import com.andrews.weather.ui.map_screen.MapScreen
import com.andrews.weather.ui.map_screen.MapViewModel
import com.andrews.weather.util.ConstValues.NAV_KEY_CITY_NAME


@Composable
fun NavGraph(
    navController: NavHostController,
    mapViewModel: MapViewModel,
) {
    NavHost(navController = navController, startDestination = Screen.MapScreen.route) {
        composable(Screen.MapScreen.route) {
            MapScreen(
                viewModel = mapViewModel
            )
        }

        composable(
            route = Screen.DetailCityWeather.route,
            arguments = listOf(
                navArgument(NAV_KEY_CITY_NAME) {
                    type = NavType.StringType
                }
            )
        ) {
            DetailCityWeatherScreen()
        }
    }
}