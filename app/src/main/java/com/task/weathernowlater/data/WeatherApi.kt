package com.task.weathernowlater.data

import com.task.weathernowlater.data.model.CityWeather
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
        @GET("weather")
        suspend fun getCityWeather(
            @Query("q") city: String,
            @Query("appid") apiKey: String,
            @Query("units") units: String = "metric" // metric, imperial, kelvin
        ): Response<CityWeather>

} 