package com.task.weathernowlater.cityweather

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun CityWeatherScreen(
    city: String,
    viewModel: CityWeatherViewModel = hiltViewModel()
) {
    val weather by viewModel.weather
    val isLoading by viewModel.isLoading
    val error by viewModel.error

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (isLoading) {
            CircularProgressIndicator()
        } else if (error != null) {
            Text(error ?: "", color = MaterialTheme.colorScheme.error)
        } else if (weather != null) {
            Text(text = weather!!.name, style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Temperature: ${weather!!.main.temp}°C")
            Text(text = "Humidity: ${weather!!.main.humidity}%")
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = weather!!.weather.firstOrNull()?.description ?: "")
        }
    }
} 