package com.task.features.domain.repository.Forcast

import com.task.data.model.ForecastItem

interface ForecastRepository {
    suspend fun getForecast(city: String): Result<List<ForecastItem>>
} 