package com.task.weathernowlater.cityinput.usecase

import com.task.weathernowlater.data.model.CityWeather
import com.task.weathernowlater.data.WeatherRepository
import javax.inject.Inject

class GetCityWeatherUseCase @Inject constructor(
    private val repository: WeatherRepository
) {
    suspend operator fun invoke(city: String): Result<CityWeather> {
        return repository.getCityWeather(city)
    }
} 