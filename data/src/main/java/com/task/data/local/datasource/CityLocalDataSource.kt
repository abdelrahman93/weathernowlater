package com.task.data.local.datasource

import com.task.data.local.database.CityWeatherEntity
import com.task.data.local.database.CityWeatherDao
import javax.inject.Inject

class CityLocalDataSource @Inject constructor(
    private val dao: CityWeatherDao
) {
    suspend fun saveCityWeather(entity: CityWeatherEntity) = dao.upsertCityWeather(entity)
    suspend fun getAllCities(): List<CityWeatherEntity> = dao.getAllCities()
    suspend fun deleteCityWeather(name: String) = dao.deleteCityWeather(name)
} 