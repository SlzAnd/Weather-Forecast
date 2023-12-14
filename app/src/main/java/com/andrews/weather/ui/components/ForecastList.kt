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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.andrews.weather.R
import com.andrews.weather.domain.WeatherIcon
import com.andrews.weather.domain.model.DailyWeather
import com.andrews.weather.ui.theme.Alto18
import com.andrews.weather.ui.theme.MineShaft77
import com.andrews.weather.ui.theme.White61
import com.andrews.weather.ui.theme.poppinsFontFamily
import kotlin.math.absoluteValue

@Composable
fun ForecastList(
    modifier: Modifier = Modifier,
    list: List<DailyWeather>,
) {

    Column(
        modifier = modifier
            .padding(horizontal = 33.dp),
    ) {
        (0..3).forEach {
            if (list.isNotEmpty()) {
                DailyWeatherItem(
                    dayOfWeek = list[it].dayOfTheWeek,
                    weatherIcon = list[it].icon,
                    humidity = list[it].humidity,
                    dayTemp = list[it].dayTemperature,
                    nightTemp = list[it].nightTemperature
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
                    nightTemp > 9 || nightTemp < -9
                ) {
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