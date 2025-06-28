package com.task.data.mapper

import com.task.data.model.CityWeather
import com.task.data.remote.dto.CityWeatherDto
import com.task.data.local.database.CityWeatherEntity
import com.task.data.model.Coordinates

fun CityWeatherDto.toDomain(): CityWeather {
    return CityWeather(
        name = name,
        temperature = main.temp,
        feelsLike = main.feels_like,
        humidity = main.humidity,
        pressure = main.pressure,
        windSpeed = wind.speed,
        description = weather.firstOrNull()?.description.orEmpty(),
        icon = weather.firstOrNull()?.icon.orEmpty(),
        country = sys.country,
        coordinates = Coordinates(
            latitude = coord.lat,
            longitude = coord.lon
        )
    )
}

fun CityWeather.toEntity(): CityWeatherEntity {
    return CityWeatherEntity(
        name = name,
        temp = temperature,
        icon = icon,
        timestamp = System.currentTimeMillis() / 1000L  // Convert to seconds
    )
}

fun CityWeatherEntity.toDomain(): CityWeather {
    return CityWeather(
        name = name,
        temperature = temp,
        feelsLike = temp, // Fallback for saved cities
        humidity = 0, // Not stored in entity
        pressure = 0, // Not stored in entity
        windSpeed = 0.0, // Not stored in entity
        description = "", // Not stored in entity
        icon = icon.orEmpty(),
        country = "", // Not stored in entity
        coordinates = Coordinates(0.0, 0.0) // Not stored in entity
    )
}
