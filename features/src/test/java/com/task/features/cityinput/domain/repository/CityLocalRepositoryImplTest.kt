package com.task.features.cityinput.domain.repository

import com.task.data.local.database.CityWeatherEntity
import com.task.data.local.datasource.CityLocalDataSource
import com.task.features.domain.repository.CityLocal.CityLocalRepositoryImpl
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class CityLocalRepositoryImplTest {
    private val localDataSource: CityLocalDataSource = mockk(relaxed = true)
    private lateinit var repository: CityLocalRepositoryImpl
    private val cityEntity = CityWeatherEntity("Cairo", 30.0, "01d", 1)
    private val cities = listOf(cityEntity)

    @Before
    fun setUp() {
        repository = CityLocalRepositoryImpl(localDataSource)
    }

    @Test
    fun `saveCityWeather calls localDataSource save`() = runTest {
        repository.saveCityWeather(cityEntity)
        coVerify { localDataSource.saveCityWeather(cityEntity) }
    }

    @Test
    fun `getAllCities returns cities from localDataSource`() = runTest {
        coEvery { localDataSource.getAllCities() } returns cities
        val result = repository.getAllCities()
        assertEquals(cities, result)
    }

    @Test
    fun `deleteCityWeather calls localDataSource delete`() = runTest {
        repository.deleteCityWeather("Cairo")
        coVerify { localDataSource.deleteCityWeather("Cairo") }
    }
} 