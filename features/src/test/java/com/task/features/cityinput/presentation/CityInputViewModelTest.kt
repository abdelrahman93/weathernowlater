package com.task.features.cityinput.presentation

import app.cash.turbine.test
import com.task.data.local.database.CityWeatherEntity
import com.task.features.presentation.cityinput.CityInputState
import com.task.data.model.CityWeather
import com.task.data.model.Coordinates
import com.task.domain.usecase.FetchCityWeatherUseCase
import com.task.domain.usecase.GetSavedCitiesUseCase
import com.task.domain.usecase.SaveCityWeatherUseCase
import com.task.features.domain.usecase.DeleteCityWeatherUseCase
import com.task.features.presentation.cityinput.CityInputViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class CityInputViewModelTest {
    private val fetchCityWeatherUseCase: FetchCityWeatherUseCase = mockk()
    private val saveCityWeatherUseCase: SaveCityWeatherUseCase = mockk(relaxed = true)
    private val deleteCityWeatherUseCase: DeleteCityWeatherUseCase = mockk(relaxed = true)
    private val getSavedCitiesUseCase: GetSavedCitiesUseCase = mockk()
    private val testDispatcher = StandardTestDispatcher()
    private lateinit var viewModel: CityInputViewModel
    private val testScope = TestScope(testDispatcher)

    private val cityWeather = CityWeather("Cairo", 30.0, 25.0, 1, 1010, 5.0, "Clear", "01d", "EG", Coordinates(0.0, 0.0) )
    private val cityEntity = CityWeatherEntity("Cairo", 30.0, "01d", 1)

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        coEvery { getSavedCitiesUseCase() } returns listOf(cityEntity)
        coEvery { fetchCityWeatherUseCase(any()) } returns Result.success(cityWeather)
        viewModel = CityInputViewModel(
            fetchCityWeatherUseCase,
            saveCityWeatherUseCase,
            deleteCityWeatherUseCase,
            getSavedCitiesUseCase
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `fetchWeather success updates state and navigates`() = testScope.runTest {
        coEvery { fetchCityWeatherUseCase("Cairo") } returns Result.success(cityWeather)
        coEvery { saveCityWeatherUseCase(any()) } returns Unit

        viewModel.fetchWeather("Cairo")
        advanceUntilIdle()

        viewModel.uiEvent.test {
            val event = awaitItem()
            assert(event is CityInputState.NavigateToDetails)
            assertEquals(cityWeather, (event as CityInputState.NavigateToDetails).cityWeather)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `fetchWeather with blank city does nothing`() = testScope.runTest {
        viewModel.fetchWeather("")
        advanceUntilIdle()
        // No events should be emitted
        viewModel.uiEvent.test {
            expectNoEvents()
        }
    }

    @Test
    fun `deleteCity removes city and reloads list`() = testScope.runTest {
        coEvery { deleteCityWeatherUseCase("Cairo") } returns Unit
        coEvery { getSavedCitiesUseCase() } returns emptyList()

        viewModel.deleteCity("Cairo")
        advanceUntilIdle()

        assertEquals(emptyList<CityWeatherEntity>(), viewModel.cities.value)
    }

    @Test
    fun `loadCities loads saved cities`() = testScope.runTest {
        coEvery { getSavedCitiesUseCase() } returns listOf(cityEntity)
        viewModel.fetchWeather("Cairo")
        advanceUntilIdle()
        assertEquals(listOf(cityEntity), viewModel.cities.value)
    }

    @Test
    fun `loading state is set correctly during fetchWeather`() = testScope.runTest {
        coEvery { fetchCityWeatherUseCase("Cairo") } returns Result.success(cityWeather)
        coEvery { saveCityWeatherUseCase(any()) } returns Unit

        val emissions = mutableListOf<Boolean>()
        val job = launch {
            viewModel.uiState.collect { emissions.add(it.isLoading) }
        }

        viewModel.fetchWeather("Cairo")
        advanceUntilIdle()
        kotlinx.coroutines.delay(10) // Give time for the last emission
        job.cancel()

        assert(emissions.contains(true)) { "Should emit loading true" }
    }
} 