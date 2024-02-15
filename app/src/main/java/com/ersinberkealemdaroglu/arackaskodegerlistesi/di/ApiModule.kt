package com.ersinberkealemdaroglu.arackaskodegerlistesi.di

import com.ersinberkealemdaroglu.arackaskodegerlistesi.data.remote.service.InsureService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    @Singleton
    fun provideInsureService(retrofit: Retrofit): InsureService {
        return retrofit.create(InsureService::class.java)
    }
}