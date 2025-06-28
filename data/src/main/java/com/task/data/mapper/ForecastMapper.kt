package com.task.data.mapper

import com.task.data.model.ForecastItem
import com.task.data.model.ForecastMain
import com.task.data.model.ForecastWeather
import com.task.data.remote.dto.ForecastItemDto
import com.task.data.remote.dto.ForecastMainDto
import com.task.data.remote.dto.ForecastWeatherDto

fun ForecastItemDto.toDomain(): ForecastItem {
    return ForecastItem(
        dt = dt,
        main = main.toDomain(),
        weather = weather.map { it.toDomain() },
        dt_txt = dtTxt
    )
}

fun ForecastMainDto.toDomain(): ForecastMain {
    return ForecastMain(
        temp = temp,
        temp_min = tempMin,
        temp_max = tempMax
    )
}

fun ForecastWeatherDto.toDomain(): ForecastWeather {
    return ForecastWeather(
        main = main,
        description = description,
        icon = icon
    )
} 