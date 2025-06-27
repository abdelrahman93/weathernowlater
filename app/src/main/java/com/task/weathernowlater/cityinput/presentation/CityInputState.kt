package com.task.weathernowlater.cityinput.presentation

data class CityInputState(
    val city: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
) 