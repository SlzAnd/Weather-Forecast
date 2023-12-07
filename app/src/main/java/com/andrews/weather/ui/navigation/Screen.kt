package com.andrews.weather.ui.navigation

sealed class Screen(val route: String) {

    data object MapScreen: Screen("map_screen")

    data object DetailCityWeather : Screen("article_screen/{cityName}") {
        fun passCityName(name: String): String {
            return this.route.replace(
                oldValue = "{cityName}",
                newValue = name
            )
        }
    }
}
