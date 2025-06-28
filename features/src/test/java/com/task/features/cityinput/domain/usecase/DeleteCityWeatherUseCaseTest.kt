package com.task.features.cityinput.domain.usecase

import com.task.features.domain.repository.CityLocal.CityLocalRepository
import com.task.features.domain.usecase.DeleteCityWeatherUseCase
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class DeleteCityWeatherUseCaseTest {
    private val repository: CityLocalRepository = mockk(relaxed = true)
    private lateinit var useCase: DeleteCityWeatherUseCase

    @Before
    fun setUp() {
        useCase = DeleteCityWeatherUseCase(repository)
    }

    @Test
    fun `invoke calls repository delete`() = runTest {
        useCase("Cairo")
        coVerify { repository.deleteCityWeather("Cairo") }
    }
} 