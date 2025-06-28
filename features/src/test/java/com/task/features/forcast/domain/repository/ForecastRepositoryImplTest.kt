package com.task.features.forcast.domain.repository

import com.task.features.domain.repository.Forcast.ForecastRepositoryImpl
import com.task.features.domain.repository.Forcast.ForecastRepository
import com.task.data.remote.datasource.ForecastRemoteDataSource
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

class ForecastRepositoryImplTest {
    private val remoteDataSource: ForecastRemoteDataSource = mockk()
    private val repository: ForecastRepository = ForecastRepositoryImpl(remoteDataSource)
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
    fun `getForecast returns success when remote data source succeeds`() = runTest {
        coEvery { remoteDataSource.fetchForecast("Cairo") } returns Result.success(forecastResponse)
        val result = repository.getForecast("Cairo")
        assert(result.isSuccess)
        assertEquals(1, result.getOrNull()?.size)
        assertEquals("2024-05-01 12:00:00", result.getOrNull()?.firstOrNull()?.dt_txt)
    }

    @Test
    fun `getForecast returns failure when remote data source fails`() = runTest {
        val error = Exception("404")
        coEvery { remoteDataSource.fetchForecast("Cairo") } returns Result.failure(error)
        val result = repository.getForecast("Cairo")
        assert(result.isFailure)
        assertEquals(error, result.exceptionOrNull())
    }
} 