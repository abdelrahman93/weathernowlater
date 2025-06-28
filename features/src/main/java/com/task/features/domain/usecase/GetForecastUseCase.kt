package com.task.features.domain.usecase

import com.task.features.domain.repository.Forcast.ForecastRepository
import com.task.data.model.ForecastItem
import javax.inject.Inject

class GetForecastUseCase @Inject constructor(
    private val repository: ForecastRepository
) {
    suspend operator fun invoke(city: String): Result<List<ForecastItem>> {
        return repository.getForecast(city)
    }
} 