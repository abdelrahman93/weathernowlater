package com.task.features.forcast.domain.usecase

import com.task.features.domain.repository.Forcast.ForecastRepository
import com.task.features.domain.usecase.GetForecastUseCase
import com.task.data.model.ForecastItem
import com.task.data.model.ForecastMain
import com.task.data.model.ForecastWeather
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetForecastUseCaseTest {
    private val repository: ForecastRepository = mockk()
    private lateinit var useCase: GetForecastUseCase
    private val forecastItem = ForecastItem(
        dt = 1620000000,
        main = ForecastMain(temp = 25.0, temp_min = 20.0, temp_max = 28.0),
        weather = listOf(ForecastWeather(main = "Clear", description = "clear sky", icon = "01d")),
        dt_txt = "2024-05-01 12:00:00"
    )
    private val forecastList = listOf(forecastItem)

    @Before
    fun setUp() {
        useCase = GetForecastUseCase(repository)
    }

    @Test
    fun `invoke returns success when repository returns success`() = runTest {
        coEvery { repository.getForecast("Cairo") } returns Result.success(forecastList)
        val result = useCase("Cairo")
        assert(result.isSuccess)
        assertEquals(forecastList, result.getOrNull())
    }

    @Test
    fun `invoke returns failure when repository returns failure`() = runTest {
        val error = Exception("404")
        coEvery { repository.getForecast("Cairo") } returns Result.failure(error)
        val result = useCase("Cairo")
        assert(result.isFailure)
        assertEquals(error, result.exceptionOrNull())
    }
}
