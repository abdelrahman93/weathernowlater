package com.task.features.forcast.presentation

import app.cash.turbine.test
import com.task.data.model.ForecastItem
import com.task.data.model.ForecastMain
import com.task.data.model.ForecastWeather
import com.task.features.domain.usecase.GetForecastUseCase
import com.task.features.presentation.forecast.ForecastEffect
import com.task.features.presentation.forecast.ForecastIntent
import com.task.features.presentation.forecast.ForecastViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.Dispatchers
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ForecastViewModelTest {
    private val getForecastUseCase: GetForecastUseCase = mockk()
    private lateinit var viewModel: ForecastViewModel
    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher)

    private val forecastItem = ForecastItem(
        dt = 1620000000,
        main = ForecastMain(temp = 25.0, temp_min = 20.0, temp_max = 28.0),
        weather = listOf(ForecastWeather(main = "Clear", description = "clear sky", icon = "01d")),
        dt_txt = "2024-05-01 12:00:00"
    )
    private val forecastList = listOf(forecastItem)

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = ForecastViewModel(getForecastUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `processIntent LoadForecast success updates state`() = testScope.runTest {
        coEvery { getForecastUseCase("Cairo") } returns Result.success(forecastList)
        viewModel.state.test {
            awaitItem() // initial state
            viewModel.processIntent(ForecastIntent.LoadForecast("Cairo"))
            assertEquals(true, awaitItem().isLoading) // loading
            val loaded = awaitItem()
            assertEquals(false, loaded.isLoading)
            assertEquals(1, loaded.forecast.size)
            assertEquals(null, loaded.error)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `processIntent LoadForecast failure updates state and emits effect`() = testScope.runTest {
        coEvery { getForecastUseCase("Cairo") } returns Result.failure(Exception("404"))
        viewModel.state.test {
            awaitItem() // initial state
            viewModel.processIntent(ForecastIntent.LoadForecast("Cairo"))
            assertEquals(true, awaitItem().isLoading) // loading
            val errorState = awaitItem()
            assertEquals(false, errorState.isLoading)
            assertEquals("City not found", errorState.error)
            cancelAndIgnoreRemainingEvents()
        }
        viewModel.effect.test {
            val effect = awaitItem()
            assert(effect is ForecastEffect.ShowError)
            assertEquals("City not found", (effect as ForecastEffect.ShowError).message)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `processIntent LoadForecast network error updates state and emits effect`() = testScope.runTest {
        coEvery { getForecastUseCase("Cairo") } returns Result.failure(Exception("network error"))
        viewModel.state.test {
            awaitItem() // initial state
            viewModel.processIntent(ForecastIntent.LoadForecast("Cairo"))
            assertEquals(true, awaitItem().isLoading) // loading
            val errorState = awaitItem()
            assertEquals(false, errorState.isLoading)
            assertEquals("Network error. Please check connection", errorState.error)
            cancelAndIgnoreRemainingEvents()
        }
        viewModel.effect.test {
            val effect = awaitItem()
            assert(effect is ForecastEffect.ShowError)
            assertEquals("Network error. Please check connection", (effect as ForecastEffect.ShowError).message)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `processIntent LoadForecast generic error updates state and emits effect`() = testScope.runTest {
        coEvery { getForecastUseCase("Cairo") } returns Result.failure(Exception("something else"))
        viewModel.state.test {
            awaitItem() // initial state
            viewModel.processIntent(ForecastIntent.LoadForecast("Cairo"))
            assertEquals(true, awaitItem().isLoading) // loading
            val errorState = awaitItem()
            assertEquals(false, errorState.isLoading)
            assertEquals("Failed to load forecast", errorState.error)
            cancelAndIgnoreRemainingEvents()
        }
        viewModel.effect.test {
            val effect = awaitItem()
            assert(effect is ForecastEffect.ShowError)
            assertEquals("Failed to load forecast", (effect as ForecastEffect.ShowError).message)
            cancelAndIgnoreRemainingEvents()
        }
    }
} 