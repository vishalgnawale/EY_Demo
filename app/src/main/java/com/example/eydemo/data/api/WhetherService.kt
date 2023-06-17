package com.example.eydemo.data.api

import com.example.eydemo.data.modal.WhetherData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WhetherService {

    @GET("/data/2.5/weather")
    suspend fun getCityWhetherUpdate(
        @Query("q")
        city:String,
        @Query("APPID")
        apiKey:String
    ):Response<WhetherData>

}