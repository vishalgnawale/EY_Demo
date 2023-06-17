package com.example.eydemo.presentation.di.core

import com.example.eydemo.data.repository.WhetherRepositoryImpl
import com.example.eydemo.data.repository.datasource.WhetherRemoteDataSource
import com.example.eydemo.domain.repository.WhetherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RepositoryModule {
    @Singleton
    @Provides
    fun provideWhetherRepository(
        whetherRemoteDataSource: WhetherRemoteDataSource
    ):WhetherRepository{
        return WhetherRepositoryImpl(whetherRemoteDataSource)
    }
}