package com.task.data.remote.datasource

import com.task.data.remote.api.WeatherApiService
import com.task.data.remote.dto.CityWeatherDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WeatherRemoteDataSourceImpl @Inject constructor(
    private val api: WeatherApiService
) : WeatherRemoteDataSource {

    override suspend fun fetchCityWeather(city: String): Result<CityWeatherDto> =
        withContext(Dispatchers.IO) {
            try {
                val response = api.fetchCityWeather(city, com.task.core.common.Constants.API_KEY)
                if (response.isSuccessful) {
                    response.body()?.let {
                        Result.success(it)
                    } ?: Result.failure(Exception("Empty response"))
                } else {
                    Result.failure(Exception("Error ${response.code()}: ${response.message()}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
}
