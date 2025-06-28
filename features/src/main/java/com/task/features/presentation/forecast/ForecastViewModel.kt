package com.task.features.presentation.forecast

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.task.core.common.util.mapIconToEmoji
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.task.features.domain.usecase.GetForecastUseCase
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import com.task.data.model.ForecastItem

@HiltViewModel
class ForecastViewModel @Inject constructor(
    private val getForecastUseCase: GetForecastUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(ForecastState())
    val state: StateFlow<ForecastState> = _state.asStateFlow()

    private val _effect = Channel<ForecastEffect>()
    val effect = _effect.receiveAsFlow()

    fun processIntent(intent: ForecastIntent) {
        when (intent) {
            is ForecastIntent.LoadForecast -> loadForecast(intent.city)
        }
    }

    private fun loadForecast(city: String) {
        viewModelScope.launch {
            updateState { it.copy(isLoading = true, error = null) }

            getForecastUseCase(city)
                .onSuccess { items ->
                    handleForecastSuccess(items)
                }
                .onFailure { error ->
                    handleForecastError(error)
                }
        }
    }

    private fun handleForecastSuccess(items: List<ForecastItem>) {
        val forecastDays = transformToForecastDays(items)
        updateState {
            it.copy(
                isLoading = false,
                forecast = forecastDays,
                error = null
            )
        }
    }

    private fun handleForecastError(error: Throwable) {
        val errorMessage = when {
            error.message?.contains("404") == true -> "City not found"
            error.message?.contains("network") == true -> "Network error. Please check connection"
            else -> "Failed to load forecast"
        }

        updateState { it.copy(isLoading = false, error = errorMessage) }

        viewModelScope.launch {
            _effect.send(ForecastEffect.ShowError(errorMessage))
        }
    }

    private fun transformToForecastDays(items: List<ForecastItem>): List<ForecastDay> {
        val grouped = items.groupBy { it.dt_txt.substring(0, 10) }
        return grouped.entries.take(7).mapIndexed { idx, entry ->
            val dayItems = entry.value
            createForecastDay(idx, dayItems)
        }
    }

    private fun createForecastDay(index: Int, dayItems: List<ForecastItem>): ForecastDay {
        val maxTemp = dayItems.maxOfOrNull { it.main.temp_max }?.toInt()?.toString() ?: "-"
        val minTemp = dayItems.minOfOrNull { it.main.temp_min }?.toInt()?.toString() ?: "-"

        val mostFrequentIcon = dayItems
            .mapNotNull { it.weather.firstOrNull()?.icon }
            .groupingBy { it }
            .eachCount()
            .maxByOrNull { it.value }?.key

        val mostFrequentDescription = dayItems
            .mapNotNull { it.weather.firstOrNull()?.main }
            .groupingBy { it }
            .eachCount()
            .maxByOrNull { it.value }?.key ?: ""

        val date = if (index == 0) {
            "Today"
        } else {
            SimpleDateFormat("EEE", Locale.getDefault())
                .format(Date(dayItems[0].dt * 1000))
        }

        return ForecastDay(
            date = date,
            icon = mapIconToEmoji(mostFrequentIcon),
            description = mostFrequentDescription,
            maxTemp = maxTemp,
            minTemp = minTemp
        )
    }

    private fun updateState(reducer: (ForecastState) -> ForecastState) {
        _state.value = reducer(_state.value)
    }
}
