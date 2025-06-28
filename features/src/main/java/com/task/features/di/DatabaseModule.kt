package com.task.features.di

import android.content.Context
import androidx.room.Room
import com.task.data.local.database.AppDatabase
import com.task.data.local.database.CityWeatherDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): AppDatabase =
        Room.databaseBuilder(appContext, AppDatabase::class.java, "weather_db").build()

    @Provides
    fun provideCityWeatherDao(db: AppDatabase): CityWeatherDao = db.cityWeatherDao()
} 