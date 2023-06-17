package com.example.eydemo.presentation.di.core

import com.example.eydemo.domain.repository.WhetherRepository
import com.example.eydemo.domain.usecase.GetWhetherDataUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class UseCaseModule {
    @Provides
    fun provideWhetherUseCase(whetherRepository: WhetherRepository):GetWhetherDataUseCase{
        return GetWhetherDataUseCase(whetherRepository)
    }

}