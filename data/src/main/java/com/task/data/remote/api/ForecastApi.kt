package com.task.data.remote.api

import com.task.data.remote.dto.ForecastResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ForecastApi {
    @GET("forecast")
    suspend fun getForecast(
        @Query("q") city: String,
        @Query("cnt") cnt: Int = 40,
        @Query("units") units: String = "metric",
        @Query("appid") apiKey: String
    ): ForecastResponse
} 