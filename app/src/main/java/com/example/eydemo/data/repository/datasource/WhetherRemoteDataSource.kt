package com.example.eydemo.data.repository.datasource

import com.example.eydemo.data.modal.WhetherData
import retrofit2.Response

interface WhetherRemoteDataSource {
    suspend fun getCityWhetherData(city:String):Response<WhetherData>
}