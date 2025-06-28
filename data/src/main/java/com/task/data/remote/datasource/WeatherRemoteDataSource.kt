package com.task.data.remote.datasource

import com.task.data.remote.dto.CityWeatherDto

interface WeatherRemoteDataSource {
    suspend fun fetchCityWeather(city: String): Result<CityWeatherDto>
}
