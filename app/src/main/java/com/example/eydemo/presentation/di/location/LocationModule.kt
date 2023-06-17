package com.example.eydemo.presentation.di.location

import android.app.Application
import com.example.eydemo.presentation.location.LocationViewModalFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@InstallIn(ActivityComponent::class)
@Module
class LocationModule {
    @ActivityScoped
    @Provides
    fun whetherViewModelFactory(
        context:Application
    ): LocationViewModalFactory {
        return LocationViewModalFactory(context)
    }
}