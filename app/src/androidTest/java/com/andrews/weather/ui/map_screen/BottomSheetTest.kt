package com.andrews.weather.ui.map_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.unit.dp
import com.andrews.weather.domain.model.CityInfo
import com.andrews.weather.ui.components.BottomSheet
import com.andrews.weather.ui.weather_screen.DetailCityWeatherScreen
import kotlinx.coroutines.launch
import org.junit.Rule
import org.junit.Test

class BottomSheetTest {
    @get:Rule(order = 0)
    val composeTestRule = createComposeRule()

    private val testCities = listOf(
        CityInfo(
            id = 0,
            name = "TestName1",
            time = "11:11",
            temperature = 1
        ),
        CityInfo(
            id = 1,
            name = "TestName2",
            time = "12:22",
            temperature = 2
        ),
        CityInfo(
            id = 2,
            name = "TestName3",
            time = "13:33",
            temperature = 10
        ),
        CityInfo(
            id = 3,
            name = "TestName4",
            time = "14:44",
            temperature = -1
        ),
        CityInfo(
            id = 4,
            name = "TestName5",
            time = "15:55",
            temperature = -7
        ),
    )

    @OptIn(ExperimentalMaterial3Api::class)
    @Test
    fun bottomSheetFunctionsTest() {
        composeTestRule.setContent {
            val scaffoldState = rememberBottomSheetScaffoldState(
                bottomSheetState = SheetState(
                    skipHiddenState = false,
                    skipPartiallyExpanded = false,
                    initialValue = SheetValue.PartiallyExpanded
                )
            )

            val scope = rememberCoroutineScope()
            val isNavigateToDetailScreen = remember {
                mutableStateOf(false)
            }


            BottomSheetScaffold(
                scaffoldState = scaffoldState,
                sheetContent = {
                    BottomSheet(
                        cities = testCities,
                        onItemClick = {
                            isNavigateToDetailScreen.value = !isNavigateToDetailScreen.value
                        }
                    )
                },
                sheetDragHandle = {
                    Column {
                        Spacer(modifier = Modifier.height(13.dp))
                        Box(
                            modifier = Modifier
                                .semantics { contentDescription = "DragHandle" }
                                .clickable { scope.launch { scaffoldState.bottomSheetState.expand() } }
                                .width(141.dp)
                                .height(7.dp)
                                .background(
                                    color = Color.White,
                                    shape = RoundedCornerShape(29.dp)
                                )
                        )
                    }
                }
            ) {
                if (isNavigateToDetailScreen.value) {
                    DetailCityWeatherScreen()
                    scope.launch { scaffoldState.bottomSheetState.partialExpand() }
                }
            }
        }

        composeTestRule.onNodeWithText("TestName5").assertIsNotDisplayed() // bottom sheet content initially hide
        composeTestRule.onNodeWithContentDescription("DragHandle").performClick() // show bottom sheet content
        composeTestRule.onNodeWithText("TestName5").assertIsDisplayed() // content is visible
        composeTestRule.onNodeWithText("TestName3").performClick() // click on item to open detailed screen
        composeTestRule.onNodeWithContentDescription("loading icon").assertIsDisplayed() // loading screen is visible
        composeTestRule.onNodeWithContentDescription("background image").assertIsDisplayed() // background image shows in detail screen
    }
}