package com.task.features.cityinput.domain.usecase

import com.task.data.local.database.CityWeatherEntity
import com.task.domain.usecase.SaveCityWeatherUseCase
import com.task.features.domain.repository.CityLocal.CityLocalRepository
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class SaveCityWeatherUseCaseTest {
    private val repository: CityLocalRepository = mockk(relaxed = true)
    private lateinit var useCase: SaveCityWeatherUseCase

    private val entity = CityWeatherEntity("Cairo", 30.0, "01d", 1)

    @Before
    fun setUp() {
        useCase = SaveCityWeatherUseCase(repository)
    }

    @Test
    fun `invoke calls repository save`() = runTest {
        useCase(entity)
        coVerify { repository.saveCityWeather(entity) }
    }
} 