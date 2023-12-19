package com.andrews.weather.mapper_tests

import com.andrews.weather.mapper_tests.TestData.testCitiesDto
import com.andrews.weather.mapper_tests.TestData.testCityList
import com.andrews.weather.data.mappers.toCityInfoList
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class CityMapperTest {



    @Test
    fun `Correct transformation CitiesDto to CityInfo list`() {
        val resultList = testCitiesDto.toCityInfoList()
        resultList.forEachIndexed { index, cityInfo ->
            assertThat(cityInfo.id).isEqualTo(testCityList[index].id)
            assertThat(cityInfo.name).isEqualTo(testCityList[index].name)
            assertThat(cityInfo.time).isEqualTo("16:20")
            assertThat(cityInfo.temperature).isEqualTo(testCityList[index].main.temp.toInt())
        }
    }
}