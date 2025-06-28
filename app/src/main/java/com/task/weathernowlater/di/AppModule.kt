package com.task.weathernowlater.di

import com.task.data.remote.api.WeatherApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideWeatherApi(retrofit: Retrofit): WeatherApiService =
        retrofit.create(WeatherApiService::class.java)

}



