package com.example.eydemo.presentation.di.whether

import com.example.eydemo.domain.usecase.GetWhetherDataUseCase
import com.example.eydemo.presentation.whether.viewmodal.WhetherViewModalFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@InstallIn(ActivityComponent::class)
@Module
class WhetherModule {
    @ActivityScoped
    @Provides
    fun whetherViewModelFactory(
        getWhetherDataUseCase: GetWhetherDataUseCase
    ):WhetherViewModalFactory{
        return WhetherViewModalFactory(getWhetherDataUseCase)
    }
}