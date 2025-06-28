package com.task.data.remote.datasource

import com.task.data.remote.dto.ForecastResponse


interface ForecastRemoteDataSource {
    suspend fun fetchForecast(city: String): Result<ForecastResponse>
} 