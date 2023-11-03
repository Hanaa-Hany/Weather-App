package com.hanaahany.weatherapp.network

import com.hanaahany.weatherapp.model.WeatherResponse
import retrofit2.Response

interface RemoteSource {
    suspend fun makeNetworkCall( lat:Double, lon:Double):Response<WeatherResponse>
}