package com.andrews.weather.ui.weather_screen

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.andrews.weather.R
import com.andrews.weather.ui.components.ForecastList
import com.andrews.weather.ui.theme.MineShaft77
import com.andrews.weather.ui.theme.White61
import com.andrews.weather.ui.theme.poppinsFontFamily
import com.andrews.weather.ui.theme.white_opacity_89
import kotlin.math.absoluteValue

@Composable
fun DetailCityWeatherScreen(
    viewModel: WeatherViewModel
) {
    val state by viewModel.state.collectAsState()

    val infiniteTransition = rememberInfiniteTransition("rotation")
    val angle by infiniteTransition.animateFloat(
        initialValue = 0F,
        targetValue = 360F,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = LinearEasing)
        ), label = ""
    )

    var isLoading by rememberSaveable {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = state.currentWeather, key2 = state.dailyWeatherList) {
        isLoading = state.currentWeather == null && state.dailyWeatherList.isEmpty()
    }

    if (state.isConnected) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.city_bg),
                contentDescription = "background image",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .matchParentSize()
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MineShaft77)
            )

            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(top = 40.dp, start = 20.dp)
                ) {
                    val temperature = state.currentWeather?.temperature

                    if (temperature != null && temperature < 0) {
                        Text(
                            text = "-",
                            style = TextStyle(
                                fontSize = 27.sp,
                                fontFamily = poppinsFontFamily,
                                fontWeight = FontWeight(500),
                                lineHeight = 40.5.sp,
                                color = Color.White
                            )
                        )
                    }
                    Text(
                        text = temperature?.absoluteValue.toString(),
                        style = TextStyle(
                            fontSize = 101.sp,
                            fontFamily = poppinsFontFamily,
                            fontWeight = FontWeight(500),
                            lineHeight = 151.5.sp,
                            color = Color.White
                        )
                    )

                    Text(
                        text = "C",
                        style = TextStyle(
                            fontSize = 30.sp,
                            fontFamily = poppinsFontFamily,
                            fontWeight = FontWeight(400),
                            lineHeight = 45.sp,
                            color = Color.White
                        ),
                        modifier = Modifier
                            .padding(top = 20.dp)
                            .align(Alignment.Top)
                    )
                    Text(
                        text = "/F",
                        style = TextStyle(
                            fontSize = 30.sp,
                            fontFamily = poppinsFontFamily,
                            fontWeight = FontWeight(400),
                            lineHeight = 45.sp,
                            color = White61
                        ),
                        modifier = Modifier
                            .padding(top = 20.dp)
                            .align(Alignment.Top)
                    )
                }

                Text(
                    text = state.cityName.uppercase(),
                    style = TextStyle(
                        fontSize = 58.sp,
                        fontFamily = poppinsFontFamily,
                        fontWeight = FontWeight(500),
                        lineHeight = 87.sp,
                        color = Color.White
                    )
                )

                Text(
                    text = state.currentWeather?.dayOfTheWeek!!.uppercase(),
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontFamily = poppinsFontFamily,
                        fontWeight = FontWeight(500),
                        lineHeight = 21.sp,
                        color = White61
                    )
                )

                Text(
                    text = state.currentWeather?.time!!,
                    style = TextStyle(
                        fontSize = 25.sp,
                        fontFamily = poppinsFontFamily,
                        fontWeight = FontWeight(500),
                        lineHeight = 37.5.sp,
                        color = Color.White
                    )
                )

                Text(
                    text = state.currentWeather?.weatherDescription!!,
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontFamily = poppinsFontFamily,
                        fontWeight = FontWeight(500),
                        lineHeight = 21.sp,
                        color = White61
                    ),
                    modifier = Modifier
                        .padding(top = 6.dp)
                )

                Spacer(modifier = Modifier.height(112.dp))

                ForecastList(list = state.dailyWeatherList)
            }
        }

        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = white_opacity_89),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    modifier = Modifier
                        .graphicsLayer { rotationZ = angle },
                    painter = painterResource(id = R.drawable.radial_progress),
                    contentDescription = "loading icon"
                )
            }
        }
    }
}