package com.task.features.di

import com.task.features.domain.repository.CityLocal.CityLocalRepository
import com.task.features.domain.repository.CityWeather.CityWeatherRepository
import com.task.data.remote.datasource.ForecastRemoteDataSource
import com.task.data.remote.datasource.WeatherRemoteDataSource
import com.task.data.remote.datasource.WeatherRemoteDataSourceImpl
import com.task.features.domain.repository.CityLocal.CityLocalRepositoryImpl
import com.task.features.domain.repository.CityWeather.CityWeatherRepositoryImpl
import com.task.features.domain.repository.Forcast.ForecastRepositoryImpl
import com.task.data.remote.datasource.ForecastRemoteDataSourceImpl
import com.task.features.domain.repository.Forcast.ForecastRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindCityWeatherRepository(
        cityWeatherRepositoryImpl: CityWeatherRepositoryImpl
    ): CityWeatherRepository

    @Binds
    @Singleton
    abstract fun bindCityLocalRepository(
        cityLocalRepositoryImpl: CityLocalRepositoryImpl
    ): CityLocalRepository

    @Binds
    abstract fun bindWeatherRemoteDataSource(
        impl: WeatherRemoteDataSourceImpl
    ): WeatherRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindForecastRepository(
        impl: ForecastRepositoryImpl
    ): ForecastRepository

    @Binds
    @Singleton
    abstract fun bindForecastRemoteDataSource(
        impl: ForecastRemoteDataSourceImpl
    ): ForecastRemoteDataSource
}