package com.task.features.domain.repository.CityLocal

import com.task.data.local.database.CityWeatherEntity
import com.task.data.local.datasource.CityLocalDataSource
import javax.inject.Inject

class CityLocalRepositoryImpl @Inject constructor(
    private val localDataSource: CityLocalDataSource
) : CityLocalRepository {

    override suspend fun saveCityWeather(entity: CityWeatherEntity) {
        localDataSource.saveCityWeather(entity)
    }

    override suspend fun getAllCities(): List<CityWeatherEntity> {
        return localDataSource.getAllCities()
    }

    override suspend fun deleteCityWeather(name: String) {
        localDataSource.deleteCityWeather(name)
    }
}
