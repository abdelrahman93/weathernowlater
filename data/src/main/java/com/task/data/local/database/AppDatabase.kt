package com.task.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CityWeatherEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cityWeatherDao(): CityWeatherDao
} 