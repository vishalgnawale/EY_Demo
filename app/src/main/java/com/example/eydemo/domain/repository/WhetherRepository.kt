package com.example.eydemo.domain.repository

import com.example.eydemo.data.api.Resource
import com.example.eydemo.data.modal.WhetherData

interface WhetherRepository {
    suspend fun getWhetherData(city:String):Resource<WhetherData>

}