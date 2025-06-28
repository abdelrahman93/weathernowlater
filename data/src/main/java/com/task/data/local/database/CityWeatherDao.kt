package com.task.data.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CityWeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertCityWeather(city: CityWeatherEntity)

    @Query("SELECT * FROM city_weather ORDER BY timestamp DESC")
    suspend fun getAllCities(): List<CityWeatherEntity>

    @Query("DELETE FROM city_weather WHERE name = :name")
    suspend fun deleteCityWeather(name: String)
} 