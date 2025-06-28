package com.task.features.presentation.cityinput

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.task.data.local.database.CityWeatherEntity
import com.task.data.model.CityWeather
import com.task.data.mapper.toEntity
import com.task.domain.usecase.FetchCityWeatherUseCase
import com.task.domain.usecase.SaveCityWeatherUseCase
import com.task.features.domain.usecase.DeleteCityWeatherUseCase
import com.task.domain.usecase.GetSavedCitiesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CityInputViewModel @Inject constructor(
    private val fetchCityWeatherUseCase: FetchCityWeatherUseCase,
    private val saveCityWeatherUseCase: SaveCityWeatherUseCase,
    private val deleteCityWeatherUseCase: DeleteCityWeatherUseCase,
    private val getSavedCitiesUseCase: GetSavedCitiesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(CityInputUiState())
    val uiState: StateFlow<CityInputUiState> = _uiState.asStateFlow()

    private val _uiEvent = Channel<CityInputState>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private val _cities = MutableStateFlow<List<CityWeatherEntity>>(emptyList())
    val cities: StateFlow<List<CityWeatherEntity>> = _cities.asStateFlow()

    init {
        loadCities()
    }

    fun fetchWeather(city: String) {
        if (city.isBlank()) return

        viewModelScope.launch {
            setLoadingState(true)

            handleWeatherFetch(city)

            setLoadingState(false)
        }
    }

    fun deleteCity(name: String) {
        viewModelScope.launch {
            deleteCityWeatherUseCase(name)
            loadCities()
        }
    }

    private suspend fun handleWeatherFetch(city: String) {
        fetchCityWeatherUseCase(city)
            .onSuccess { weather ->
                saveWeatherAndNavigate(weather)
            }
            .onFailure { error ->
                handleFetchError(error)
            }
    }

    private suspend fun saveWeatherAndNavigate(weather: CityWeather) {
        // Convert CityWeather to CityWeatherEntity for saving
        val entity = weather.toEntity()

        saveCityWeatherUseCase(entity)
        loadCities()
        _uiEvent.send(CityInputState.NavigateToDetails(weather))
    }

    private suspend fun handleFetchError(error: Throwable) {
        val errorMessage = when {
            error.message?.contains("404") == true -> "City not found"
            error.message?.contains("network") == true -> "Network error. Please check your connection"
            else -> "An error occurred. Please try again"
        }
        _uiEvent.send(CityInputState.ShowError(errorMessage))
    }

    private fun loadCities() {
        viewModelScope.launch {
            _cities.value = getSavedCitiesUseCase()
        }
    }

    private fun setLoadingState(isLoading: Boolean) {
        _uiState.value = _uiState.value.copy(isLoading = isLoading)
    }
}
