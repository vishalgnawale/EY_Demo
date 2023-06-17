package com.example.eydemo.domain.usecase

import com.example.eydemo.data.api.Resource
import com.example.eydemo.data.modal.WhetherData
import com.example.eydemo.domain.repository.WhetherRepository

class GetWhetherDataUseCase(private val whetherRepository: WhetherRepository) {
    suspend fun getCityWhetherData(city:String):Resource<WhetherData>?=whetherRepository.getWhetherData(city)
}