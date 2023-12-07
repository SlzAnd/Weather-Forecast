package com.andrews.weather.ui.components

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.andrews.weather.R
import com.andrews.weather.domain.WeatherIcon
import com.andrews.weather.domain.model.DailyWeather
import com.andrews.weather.ui.theme.Alto18
import com.andrews.weather.ui.theme.MineShaft
import com.andrews.weather.ui.theme.MineShaft77
import com.andrews.weather.ui.theme.White61
import com.andrews.weather.ui.theme.poppinsFontFamily
import java.util.Locale
import kotlin.math.absoluteValue

@Composable
fun ForecastList(
    modifier: Modifier = Modifier,
    list: List<DailyWeather>,
) {
    val lazyColumnState = rememberLazyListState()

    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        LazyColumn(
            state = lazyColumnState,
            modifier = modifier
                .padding(horizontal = 33.dp)
        ) {
            items(items = list, key = {it.dayOfTheWeek}) {weather ->
                DailyWeatherItem(
                    dayOfWeek = weather.dayOfTheWeek,
                    weatherIcon = weather.icon,
                    humidity = weather.humidity,
                    dayTemp = weather.dayTemperature,
                    nightTemp = weather.nightTemperature
                )
            }
        }
    }
}

@Composable
fun DailyWeatherItem(
    dayOfWeek: String,
    weatherIcon: WeatherIcon,
    humidity: Int,
    dayTemp: Int,
    nightTemp: Int
) {

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp, bottom = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = dayOfWeek,
                style = TextStyle(
                    fontSize = 19.sp,
                    fontFamily = poppinsFontFamily,
                    fontWeight = FontWeight(500),
                    lineHeight = 28.5.sp,
                    color = Color.White
                ),
                modifier = Modifier
                    .weight(0.4f)
            )

            // icon + humidity row
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(0.33f)
            ) {
                Image(
                    painter = painterResource(id = weatherIcon.iconRes),
                    contentDescription = "weather icon",
                     modifier = Modifier
                         .size(28.dp)
                )

                Spacer(modifier = Modifier.width(6.dp))

                Text(
                    text = "$humidity%",
                    style = TextStyle(
                        fontSize = 10.sp,
                        fontFamily = poppinsFontFamily,
                        fontWeight = FontWeight(500),
                        lineHeight = 15.sp,
                        color = Color.White
                    )
                )
            }

            // temperatures row
            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.weight(0.33f)
            ) {
                Text(
                    text = dayTemp.toString(),
                    style = TextStyle(
                        fontSize = 19.sp,
                        fontFamily = poppinsFontFamily,
                        fontWeight = FontWeight(500),
                        lineHeight = 28.5.sp,
                        color = Color.White
                    )
                )

                // space between temperatures
                if (dayTemp > 9 || dayTemp < -9 ||
                    nightTemp > 9 || nightTemp < -9) {
                    Spacer(modifier = Modifier.width(15.dp))
                } else {
                    Spacer(modifier = Modifier.width(30.dp))
                }

                Text(
                    text = nightTemp.toString(),
                    style = TextStyle(
                        fontSize = 19.sp,
                        fontFamily = poppinsFontFamily,
                        fontWeight = FontWeight(500),
                        lineHeight = 28.5.sp,
                        color = White61
                    )
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(color = Alto18)
        )
    }
    
}

val testList = listOf(
    DailyWeather(
        "Monday",
        WeatherIcon.fromIconCode(201),
        humidity = 80,
        dayTemperature = -1,
        nightTemperature = -7
    ),
    DailyWeather(
        "Tuesday",
        WeatherIcon.fromIconCode(301),
        humidity = 40,
        dayTemperature = 1,
        nightTemperature = -2
    ),
    DailyWeather(
        "Wednesday",
        WeatherIcon.fromIconCode(501),
        humidity = 50,
        dayTemperature = -10,
        nightTemperature = -17
    ),
    DailyWeather(
        "Thursday",
        WeatherIcon.fromIconCode(601),
        humidity = 70,
        dayTemperature = 5,
        nightTemperature = 0
    ),
)


@Preview(showSystemUi = true, showBackground = true)
@Composable
fun Prev() {
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
                val temperature = -3

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
                    ),
                    modifier = Modifier
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
                text = "Kharkiv".uppercase(),
                style = TextStyle(
                    fontSize = 58.sp,
                    fontFamily = poppinsFontFamily,
                    fontWeight = FontWeight(500),
                    lineHeight = 87.sp,
                    color = Color.White
                )
            )

            Text(
                text = "Wednesday".uppercase(),
                style = TextStyle(
                    fontSize = 14.sp,
                    fontFamily = poppinsFontFamily,
                    fontWeight = FontWeight(500),
                    lineHeight = 21.sp,
                    color = White61
                )
            )

            Text(
                text = "22:00",
                style = TextStyle(
                    fontSize = 25.sp,
                    fontFamily = poppinsFontFamily,
                    fontWeight = FontWeight(500),
                    lineHeight = 37.5.sp,
                    color = Color.White
                )
            )

            Text(
                text = "Mostly cloudy",
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

            ForecastList(list = testList)
        }
    }
}