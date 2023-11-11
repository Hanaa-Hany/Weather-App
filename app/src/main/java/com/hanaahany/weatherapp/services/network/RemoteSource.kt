package com.hanaahany.weatherapp.services.network

import com.hanaahany.weatherapp.model.WeatherResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface RemoteSource {
    suspend fun makeNetworkCall( lat:Double, lon:Double,units:String,lang:String):Flow<WeatherResponse>

}