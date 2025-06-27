package com.task.weathernowlater.data

import com.task.weathernowlater.data.model.CityWeather

interface WeatherRepository {
    suspend fun getCityWeather(city: String): Result<CityWeather>
} 