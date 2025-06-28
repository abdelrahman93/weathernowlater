package com.task.data.model


data class CityWeather(
    val name: String,
    val temperature: Double,
    val feelsLike: Double,
    val humidity: Int,
    val pressure: Int,
    val windSpeed: Double,
    val description: String,
    val icon: String,
    val country: String,
    val coordinates: Coordinates
)

data class Coordinates(
    val latitude: Double,
    val longitude: Double
)
