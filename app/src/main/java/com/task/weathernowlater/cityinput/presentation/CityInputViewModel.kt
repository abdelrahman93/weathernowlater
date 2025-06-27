package com.task.weathernowlater.cityinput.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.task.weathernowlater.cityinput.usecase.GetCityWeatherUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CityInputViewModel @Inject constructor(
    private val getCityWeatherUseCase: GetCityWeatherUseCase
) : ViewModel() {
    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading
    private val _error = mutableStateOf<String?>(null)
    val error: State<String?> = _error

    fun getWeather(city: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            val result = getCityWeatherUseCase(city)
            if (result.isSuccess) {
                onResult(true)
            } else {
                _error.value = result.exceptionOrNull()?.message ?: "Unknown error"
                onResult(false)
            }
            _isLoading.value = false
        }
    }
} 