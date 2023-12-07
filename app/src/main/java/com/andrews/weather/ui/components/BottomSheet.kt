package com.andrews.weather.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.andrews.weather.domain.model.CityInfo
import com.andrews.weather.ui.theme.Alto
import com.andrews.weather.ui.theme.MineShaft
import com.andrews.weather.ui.theme.White61
import com.andrews.weather.ui.theme.poppinsFontFamily
import kotlin.math.absoluteValue

@Composable
fun BottomSheet(
    modifier: Modifier = Modifier,
    cities: List<CityInfo>,
    onItemClick: (name: String) -> Unit
) {
    val lazyColumnState = rememberLazyListState()
    Box(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight(0.6f) // 60% expand of bottom sheet
        .background(color = MineShaft)
        .padding(top = 33.dp, start = 33.dp, end = 33.dp),) {
        LazyColumn(
            modifier = modifier
                .fillMaxSize(),
            state = lazyColumnState
        ) {
            items(items = cities, key = { it.id }) {
                CityItem(
                    modifier = Modifier
                        .clickable { onItemClick(it.name) },
                    name = it.name,
                    time = it.time,
                    temperature = it.temperature
                )
            }
        }
    }

}

@Composable
fun CityItem(
    modifier: Modifier = Modifier,
    name: String,
    time: String,
    temperature: Int
) {
    Column {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 12.dp, start = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = name,
                    style = TextStyle(
                        fontSize = 27.sp,
                        fontFamily = poppinsFontFamily,
                        fontWeight = FontWeight(500),
                        lineHeight = 40.5.sp,
                        color = Color.White
                    )
                )

                Text(
                    text = time,
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontFamily = poppinsFontFamily,
                        fontWeight = FontWeight(500),
                        lineHeight = 21.sp,
                        color = White61
                    )
                )
            }
            Row(
                modifier = Modifier,
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (temperature < 0) {
                    Text(
                        modifier = Modifier,
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
                Spacer(modifier = Modifier.width(7.dp))

                Text(
                    text = temperature.absoluteValue.toString(),
                    style = TextStyle(
                        fontSize = 53.sp,
                        fontFamily = poppinsFontFamily,
                        fontWeight = FontWeight(500),
                        lineHeight = 79.5.sp,
                        color = Color.White
                    )
                )

                Spacer(modifier = Modifier.width(6.dp))

                Text(
                    modifier = Modifier
                        .padding(end = 8.dp, top = 8.dp)
                        .align(Alignment.Top),
                    text = "C",
                    style = TextStyle(
                        fontSize = 21.sp,
                        fontFamily = poppinsFontFamily,
                        fontWeight = FontWeight(400),
                        lineHeight = 31.5.sp,
                        color = White61
                    )
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(color = Alto)
        )
    }
}