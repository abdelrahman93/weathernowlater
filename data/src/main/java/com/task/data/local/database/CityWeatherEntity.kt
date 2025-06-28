package com.task.data.local.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "city_weather")
data class CityWeatherEntity(
    @PrimaryKey val name: String,
    val temp: Double,
    val icon: String?,
    val timestamp: Long
) 