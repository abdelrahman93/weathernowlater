package com.task.features.domain.repository.CityLocal

import com.task.data.local.database.CityWeatherEntity


interface CityLocalRepository {
    suspend fun saveCityWeather(entity: CityWeatherEntity)
    suspend fun getAllCities(): List<CityWeatherEntity>
    suspend fun deleteCityWeather(name: String)
}
