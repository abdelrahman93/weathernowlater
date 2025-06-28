package com.task.features.presentation.forecast

data class ForecastState(
    val isLoading: Boolean = false,
    val forecast: List<ForecastDay> = emptyList(),
    val error: String? = null
)

sealed class ForecastIntent {
    data class LoadForecast(val city: String) : ForecastIntent()
}

sealed class ForecastEffect {
    data class ShowError(val message: String) : ForecastEffect()
}