package com.task.weathernowlater.cityweather

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.task.weathernowlater.data.model.CityWeather
import com.task.weathernowlater.cityinput.usecase.GetCityWeatherUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CityWeatherViewModel @Inject constructor(
    private val getCityWeatherUseCase: GetCityWeatherUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _weather = mutableStateOf<CityWeather?>(null)
    val weather: State<CityWeather?> = _weather
    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading
    private val _error = mutableStateOf<String?>(null)
    val error: State<String?> = _error

    init {
        val city = savedStateHandle.get<String>("city")
        if (!city.isNullOrBlank()) {
            fetchWeather(city)
        } else {
            _error.value = "City not found."
        }
    }

    private fun fetchWeather(city: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            val result = getCityWeatherUseCase(city)
            if (result.isSuccess) {
                _weather.value = result.getOrNull()
            } else {
                _error.value = result.exceptionOrNull()?.message ?: "Unknown error"
            }
            _isLoading.value = false
        }
    }
} 