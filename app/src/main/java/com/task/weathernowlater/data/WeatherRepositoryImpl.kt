package com.task.weathernowlater.data

import android.util.Log
import com.task.weathernowlater.data.model.CityWeather
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val api: WeatherApi
) : WeatherRepository {

    companion object {
        private const val API_KEY = "d80b82fd408395566243e426e02a899d"
    }

    override suspend fun getCityWeather(city: String): Result<CityWeather> {
        return try {
            val response = api.getCityWeather(city, API_KEY)
            if (response.isSuccessful) {
                Log.d("TAG", "check response --> getCityWeather:success ")
                Log.d("TAG", "check response --> getCityWeather:success "+response.body())

                response.body()?.let {
                    Result.success(it)
                } ?: Result.failure(Exception("Empty response"))
            } else {
                Log.d("TAG", "check response --> getCityWeather: fail")

                Result.failure(Exception("Error ${response.code()}: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
