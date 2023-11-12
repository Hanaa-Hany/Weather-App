package com.hanaahany.weatherapp.services.network

import com.hanaahany.weatherapp.services.model.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherServices: RemoteSource {
    @GET("onecall")
    suspend fun currentWeather(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("units")units:String="metric",
        @Query("lang")lang:String="en",
        @Query("appid") appid:String ="5f40589c146e66738eacada978281f07"):Response<WeatherResponse>


}