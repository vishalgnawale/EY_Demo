package com.example.eydemo.presentation.di.core

import  com.example.eydemo.BuildConfig
import com.example.eydemo.data.api.WhetherService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {
    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BuildConfig.BASE_URL)
            .build()
    }
    @Provides
    @Singleton
    fun provideWhetherService(retrofit: Retrofit):WhetherService{
        return retrofit.create(WhetherService::class.java)
    }
}
