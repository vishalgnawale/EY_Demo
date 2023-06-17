package com.example.eydemo.data.repository

import android.util.Log
import com.example.eydemo.data.api.Resource
import com.example.eydemo.data.modal.WhetherData
import com.example.eydemo.data.repository.datasource.WhetherRemoteDataSource
import com.example.eydemo.domain.repository.WhetherRepository
import com.example.eydemo.presentation.whether.activity.APPConstants

class WhetherRepositoryImpl (
    private val whetherRemoteDataSource: WhetherRemoteDataSource
        ): WhetherRepository {
    override suspend fun getWhetherData(city: String): Resource<WhetherData> {
        return getWhetherDataFromApi(city)
    }

    private suspend fun getWhetherDataFromApi(city:String):Resource<WhetherData>{
        try {
            val response=whetherRemoteDataSource.getCityWhetherData(city)
            val body=response.body()
            if(body!=null&& response.isSuccessful){
               return  Resource.Success(data = body)
            }else{
                return Resource.Error(errorMessage = "Something went wrong")
            }
        }catch (e:Exception){
            Log.d(APPConstants.TAG, "getArtistFromApi: ${e.message.toString()}")
            return Resource.Error(errorMessage = "Something went wrong")
        }
    }


}