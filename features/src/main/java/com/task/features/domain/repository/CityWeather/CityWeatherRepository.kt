package com.task.features.domain.repository.CityWeather

import com.task.data.model.CityWeather

interface CityWeatherRepository {
    suspend fun fetchCityWeather(city: String): Result<CityWeather>
} 