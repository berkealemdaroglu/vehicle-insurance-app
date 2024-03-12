package com.ersinberkealemdaroglu.arackaskodegerlistesi.di

import android.content.Context
import com.airbnb.lottie.BuildConfig
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.ersinberkealemdaroglu.arackaskodegerlistesi.data.remote.service.InsureService
import com.ersinberkealemdaroglu.arackaskodegerlistesi.utils.BASE_URL
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            //level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @Singleton
    fun provideInsureService(retrofit: Retrofit): InsureService {
        return retrofit.create(InsureService::class.java)
    }

    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder().baseUrl(BASE_URL).client(okHttpClient).addConverterFactory(GsonConverterFactory.create(gson)).build()

    @Provides
    @Singleton
    fun provideGson(): Gson = Gson()

    @Provides
    @Singleton
    fun provideOkHttpClient(@ApplicationContext appContext: Context, loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        val builder = OkHttpClient.Builder().addInterceptor(loggingInterceptor).readTimeout(30, TimeUnit.SECONDS).connectTimeout(30, TimeUnit.SECONDS)
        if (BuildConfig.DEBUG) {
            builder.addInterceptor(ChuckerInterceptor.Builder(context = appContext).build())
        }
        return builder.build()
    }
}