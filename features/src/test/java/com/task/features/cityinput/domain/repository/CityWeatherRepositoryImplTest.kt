package com.task.features.cityinput.domain.repository

import com.task.data.model.CityWeather
import com.task.data.model.Coordinates
import com.task.data.remote.datasource.WeatherRemoteDataSource
import com.task.data.remote.dto.CityWeatherDto
import com.task.data.remote.dto.CoordDto
import com.task.data.remote.dto.WeatherDto
import com.task.data.remote.dto.MainDto
import com.task.data.remote.dto.WindDto
import com.task.data.remote.dto.CloudsDto
import com.task.data.remote.dto.SysDto
import com.task.features.domain.repository.CityWeather.CityWeatherRepositoryImpl
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class CityWeatherRepositoryImplTest {
    private val remoteDataSource: WeatherRemoteDataSource = mockk()
    private val repository = CityWeatherRepositoryImpl(remoteDataSource)
    private val cityWeather = CityWeather("Cairo", 30.0, 25.0, 1, 1010, 5.0, "clear sky", "01d", "EG", Coordinates(0.0, 0.0) )
    private val cityWeatherDto = CityWeatherDto(
        coord = CoordDto(lon = 0.0, lat = 0.0),
        weather = listOf(WeatherDto(id = 800, main = "Clear", description = "clear sky", icon = "01d")),
        base = "stations",
        main = MainDto(
            temp = 30.0,
            feels_like = 25.0,
            temp_min = 28.0,
            temp_max = 32.0,
            pressure = 1010,
            humidity = 1
        ),
        visibility = 10000,
        wind = WindDto(speed = 5.0, deg = 180),
        clouds = CloudsDto(all = 0),
        dt = 0L,
        sys = SysDto(type = 1, id = 1, country = "EG", sunrise = 0L, sunset = 0L),
        timezone = 0,
        id = 1,
        name = "Cairo",
        cod = 200
    )

    @Test
    fun `fetchCityWeather returns success when remote data source succeeds`() = runTest {
        coEvery { remoteDataSource.fetchCityWeather("Cairo") } returns Result.success(cityWeatherDto)
        val result = repository.fetchCityWeather("Cairo")
        assert(result.isSuccess)
        assertEquals(cityWeather, result.getOrNull())
    }

    @Test
    fun `fetchCityWeather returns failure when remote data source fails`() = runTest {
        val error = Exception("404")
        coEvery { remoteDataSource.fetchCityWeather("Cairo") } returns Result.failure(error)
        val result = repository.fetchCityWeather("Cairo")
        assert(result.isFailure)
        assertEquals(error, result.exceptionOrNull())
    }
} 