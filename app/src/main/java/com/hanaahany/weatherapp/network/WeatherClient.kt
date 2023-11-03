package com.hanaahany.weatherapp.network

import com.hanaahany.weatherapp.model.WeatherResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object WeatherClient:RemoteSource {

    private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val productServices = retrofit.create(WeatherServices::class.java)
    override suspend fun makeNetworkCall( lat:Double, lon:Double): Response<WeatherResponse> {
        return productServices.currentWeather(lat,lon)
    }
}