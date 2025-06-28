package com.task.data

import com.task.data.remote.api.ForecastApi
import com.task.data.remote.datasource.ForecastRemoteDataSourceImpl
import com.task.data.remote.dto.ForecastResponse
import com.task.data.remote.dto.ForecastItemDto
import com.task.data.remote.dto.ForecastMainDto
import com.task.data.remote.dto.ForecastWeatherDto
import com.task.data.remote.dto.CityDto
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class ForecastRemoteDataSourceImplTest {
    private val api: ForecastApi = mockk()
    private val dataSource = ForecastRemoteDataSourceImpl(api)
    private val forecastItemDto = ForecastItemDto(
        dt = 1620000000,
        main = ForecastMainDto(temp = 25.0, tempMin = 20.0, tempMax = 28.0),
        weather = listOf(ForecastWeatherDto(main = "Clear", description = "clear sky", icon = "01d")),
        dtTxt = "2024-05-01 12:00:00"
    )
    private val forecastResponse = ForecastResponse(
        list = listOf(forecastItemDto),
        city = CityDto(name = "Cairo", country = "EG"),
        cod = "200",
        message = 0.0,
        cnt = 1
    )

    @Test
    fun `fetchForecast returns success when api call succeeds`() = runTest {
        coEvery { api.getForecast("Cairo", any(), any(), any()) } returns forecastResponse
        val result = dataSource.fetchForecast("Cairo")
        assert(result.isSuccess)
        assertEquals(forecastResponse, result.getOrNull())
    }

    @Test
    fun `fetchForecast returns failure when api call throws`() = runTest {
        val error = Exception("network error")
        coEvery { api.getForecast("Cairo", any(), any(), any()) } throws error
        val result = dataSource.fetchForecast("Cairo")
        assert(result.isFailure)
        assertEquals(error, result.exceptionOrNull())
    }
} 