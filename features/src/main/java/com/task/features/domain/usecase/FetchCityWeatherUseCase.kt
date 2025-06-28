package com.task.domain.usecase

import com.task.data.model.CityWeather
import com.task.features.domain.repository.CityWeather.CityWeatherRepository
import javax.inject.Inject

class FetchCityWeatherUseCase @Inject constructor(
    private val repository: CityWeatherRepository
) {
    suspend operator fun invoke(city: String): Result<CityWeather> {
        return repository.fetchCityWeather(city)
    }
} 