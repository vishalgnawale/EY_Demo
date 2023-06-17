package com.example.eydemo.presentation.location

import android.app.Application
import androidx.lifecycle.AndroidViewModel

class LocationViewModal(application: Application) : AndroidViewModel(application){
    private val locationData=LocationDetailsLiveData(application)
    fun getLocationData() = locationData

}