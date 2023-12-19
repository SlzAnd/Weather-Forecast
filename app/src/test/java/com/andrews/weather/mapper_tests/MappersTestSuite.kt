package com.andrews.weather.mapper_tests

import org.junit.runner.RunWith
import org.junit.runners.Suite
import org.junit.runners.Suite.SuiteClasses

@RunWith(Suite::class)
@SuiteClasses(CityMapperTest::class, WeatherMapperTest::class)
class MappersTestSuite