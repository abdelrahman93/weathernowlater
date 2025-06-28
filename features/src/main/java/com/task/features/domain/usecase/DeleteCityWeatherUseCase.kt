package com.task.features.domain.usecase

import com.task.features.domain.repository.CityLocal.CityLocalRepository
import javax.inject.Inject

class DeleteCityWeatherUseCase @Inject constructor(
    private val repository: CityLocalRepository
) {
    suspend operator fun invoke(name: String) {
        repository.deleteCityWeather(name)
    }
}
