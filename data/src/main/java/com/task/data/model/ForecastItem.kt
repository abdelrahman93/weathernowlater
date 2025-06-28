package com.task.data.model

data class ForecastItem(
    val dt: Long,
    val main: ForecastMain,
    val weather: List<ForecastWeather>,
    val dt_txt: String
)

data class ForecastMain(
    val temp: Double,
    val temp_min: Double,
    val temp_max: Double
)

data class ForecastWeather(
    val main: String,
    val description: String,
    val icon: String
)