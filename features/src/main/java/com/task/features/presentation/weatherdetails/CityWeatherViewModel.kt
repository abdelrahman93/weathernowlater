package com.task.features.presentation.weatherdetails

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.task.data.model.CityWeather
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CityWeatherViewModel @Inject constructor() : ViewModel() {
    var cityWeather: CityWeather? by mutableStateOf(null)
} 