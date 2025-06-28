package com.task.features.cityinput.domain.usecase

import com.task.data.local.database.CityWeatherEntity
import com.task.domain.usecase.GetSavedCitiesUseCase
import com.task.features.domain.repository.CityLocal.CityLocalRepository
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GetSavedCitiesUseCaseTest {
    private val repository: CityLocalRepository = mockk()
    private lateinit var useCase: GetSavedCitiesUseCase
    private val cities = listOf(CityWeatherEntity("Cairo", 30.0, "01d", 1))

    @Before
    fun setUp() {
        useCase = GetSavedCitiesUseCase(repository)
    }

    @Test
    fun `invoke returns saved cities from repository`() = runTest {
        coEvery { repository.getAllCities() } returns cities
        val result = useCase()
        assertEquals(cities, result)
    }
} 