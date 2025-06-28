package com.task.data.remote.datasource

import com.task.data.remote.api.ForecastApi
import com.task.data.remote.dto.ForecastResponse
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ForecastRemoteDataSourceImpl @Inject constructor(
    private val api: ForecastApi
) : ForecastRemoteDataSource {
    override suspend fun fetchForecast(city: String): Result<ForecastResponse> =
        withContext(Dispatchers.IO) {
            try {
                val response = api.getForecast(city, apiKey = com.task.core.common.Constants.API_KEY)
                Result.success(response)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
} 