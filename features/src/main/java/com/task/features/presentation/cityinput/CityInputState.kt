package com.task.features.presentation.cityinput

import com.task.data.model.CityWeather

//import com.task.features.cityinput.domain.model.CityWeather

sealed class CityInputState {
    data class NavigateToDetails(val cityWeather: CityWeather) : CityInputState()
    data class ShowError(val message: String) : CityInputState()

}

data class CityInputUiState(
    val isLoading: Boolean = false
)