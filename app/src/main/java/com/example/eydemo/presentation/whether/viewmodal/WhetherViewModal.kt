package com.example.eydemo.presentation.whether.viewmodal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.eydemo.domain.usecase.GetWhetherDataUseCase

class WhetherViewModal(private val getWhetherDataUseCase: GetWhetherDataUseCase) :ViewModel(){
    fun getWhetherData(city:String)= liveData {
        val whetherData=getWhetherDataUseCase.getCityWhetherData(city)
        if (whetherData != null) {
            emit(whetherData.data)
        }
    }

}