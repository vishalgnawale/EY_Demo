package com.example.eydemo.data.repository.datasourceimpl

import com.example.eydemo.data.api.WhetherService
import com.example.eydemo.data.modal.WhetherData
import com.example.eydemo.data.repository.datasource.WhetherRemoteDataSource
import retrofit2.Response

class WhetherRemoteDataSourceImpl(
    private val whetherService: WhetherService,private val apiKey:String
    ) :WhetherRemoteDataSource{

    override suspend fun getCityWhetherData(city: String): Response<WhetherData> {
        return whetherService.getCityWhetherUpdate(city,apiKey)
    }
}