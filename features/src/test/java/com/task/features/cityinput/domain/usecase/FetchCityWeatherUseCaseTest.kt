package com.task.features.cityinput.domain.usecase

import com.task.data.model.CityWeather
import com.task.data.model.Coordinates
import com.task.domain.usecase.FetchCityWeatherUseCase
import com.task.features.domain.repository.CityWeather.CityWeatherRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class FetchCityWeatherUseCaseTest {
    private val repository: CityWeatherRepository = mockk()
    private lateinit var useCase: FetchCityWeatherUseCase
    private val cityWeather = CityWeather("Cairo", 30.0, 25.0, 1, 1010, 5.0, "Clear", "01d", "EG", Coordinates(0.0, 0.0) )

    @Before
    fun setUp() {
        useCase = FetchCityWeatherUseCase(repository)
    }

    @Test
    fun `invoke returns success when repository returns success`() = runTest {
        coEvery { repository.fetchCityWeather("Cairo") } returns Result.success(cityWeather)
        val result = useCase("Cairo")
        assert(result.isSuccess)
        assertEquals(cityWeather, result.getOrNull())
    }

    @Test
    fun `invoke returns failure when repository returns failure`() = runTest {
        val error = Exception("404")
        coEvery { repository.fetchCityWeather("Cairo") } returns Result.failure(error)
        val result = useCase("Cairo")
        assert(result.isFailure)
        assertEquals(error, result.exceptionOrNull())
    }
} 