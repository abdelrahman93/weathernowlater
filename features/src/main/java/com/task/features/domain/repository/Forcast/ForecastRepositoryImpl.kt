package com.task.features.domain.repository.Forcast

import com.task.data.model.ForecastItem
import com.task.data.mapper.toDomain
import com.task.data.remote.datasource.ForecastRemoteDataSource
import javax.inject.Inject

class ForecastRepositoryImpl @Inject constructor(
    private val remoteDataSource: ForecastRemoteDataSource
) : ForecastRepository {
    override suspend fun getForecast(city: String): Result<List<ForecastItem>> {
        return remoteDataSource.fetchForecast(city)
            .mapCatching { response -> response.list.map { it.toDomain() } }
    }
} 