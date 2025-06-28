package com.task.domain.usecase

import com.task.data.local.database.CityWeatherEntity
import com.task.features.domain.repository.CityLocal.CityLocalRepository
import javax.inject.Inject

class SaveCityWeatherUseCase @Inject constructor(
    private val repository: CityLocalRepository
) {
    suspend operator fun invoke(entity: CityWeatherEntity) {
        repository.saveCityWeather(entity)
    }
}
