package com.task.features.domain.repository.CityWeather

import com.task.data.model.CityWeather
import com.task.data.mapper.toDomain
import com.task.data.remote.datasource.WeatherRemoteDataSource
import javax.inject.Inject

class CityWeatherRepositoryImpl @Inject constructor(
    private val remoteDataSource: WeatherRemoteDataSource
) : CityWeatherRepository {

    override suspend fun fetchCityWeather(city: String): Result<CityWeather> {
        return remoteDataSource.fetchCityWeather(city)
            .mapCatching { dto -> dto.toDomain() }
    }
}
