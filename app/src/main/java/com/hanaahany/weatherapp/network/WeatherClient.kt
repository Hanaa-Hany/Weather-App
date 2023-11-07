package com.hanaahany.weatherapp.network

import kotlinx.coroutines.flow.flow
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object WeatherClient:RemoteSource {

    private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val weatherServices = retrofit.create(WeatherServices::class.java)
    override suspend fun makeNetworkCall( lat:Double, lon:Double,units:String,lang:String)=flow{
        emit(weatherServices.currentWeather(lat,lon,units,lang).body()!!)
    }
}