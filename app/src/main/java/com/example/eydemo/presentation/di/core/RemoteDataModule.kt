package com.example.eydemo.presentation.di.core

import com.example.eydemo.BuildConfig
import com.example.eydemo.data.api.WhetherService
import com.example.eydemo.data.repository.datasource.WhetherRemoteDataSource
import com.example.eydemo.data.repository.datasourceimpl.WhetherRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RemoteDataModule {
    @Singleton
    @Provides
    fun provideWhetherDataSource(whetherService: WhetherService):WhetherRemoteDataSource{
        return WhetherRemoteDataSourceImpl(whetherService, BuildConfig.API_KEY)
    }
}