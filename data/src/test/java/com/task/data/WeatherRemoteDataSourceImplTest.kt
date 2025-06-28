package com.task.data

import com.task.data.remote.api.WeatherApiService
import com.task.data.remote.datasource.WeatherRemoteDataSourceImpl
import com.task.data.remote.dto.CityWeatherDto
import com.task.data.remote.dto.CoordDto
import com.task.data.remote.dto.WeatherDto
import com.task.data.remote.dto.MainDto
import com.task.data.remote.dto.WindDto
import com.task.data.remote.dto.CloudsDto
import com.task.data.remote.dto.SysDto
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import retrofit2.Response

class WeatherRemoteDataSourceImplTest {
    private val api: WeatherApiService = mockk()
    private val dataSource = WeatherRemoteDataSourceImpl(api)
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
    fun `fetchCityWeather returns success when api call succeeds`() = runTest {
        coEvery { api.fetchCityWeather("Cairo", any(), any()) } returns Response.success(cityWeatherDto)
        val result = dataSource.fetchCityWeather("Cairo")
        assertTrue(result.isSuccess)
        assertEquals(cityWeatherDto, result.getOrNull())
    }

    @Test
    fun `fetchCityWeather returns failure when api call returns empty body`() = runTest {
        coEvery { api.fetchCityWeather("Cairo", any(), any()) } returns Response.success(null)
        val result = dataSource.fetchCityWeather("Cairo")
        assertTrue(result.isFailure)
        assertEquals("Empty response", result.exceptionOrNull()?.message)
    }

    @Test
    fun `fetchCityWeather returns failure when api call is not successful`() = runTest {
        coEvery { api.fetchCityWeather("Cairo", any(), any()) } returns Response.error(404, okhttp3.ResponseBody.create(null, "Not found"))
        val result = dataSource.fetchCityWeather("Cairo")
        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull()?.message?.contains("Error 404") == true)
    }

    @Test
    fun `fetchCityWeather returns failure when api call throws`() = runTest {
        val error = Exception("network error")
        coEvery { api.fetchCityWeather("Cairo", any(), any()) } throws error
        val result = dataSource.fetchCityWeather("Cairo")
        assertTrue(result.isFailure)
        assertEquals(error, result.exceptionOrNull())
    }
} 