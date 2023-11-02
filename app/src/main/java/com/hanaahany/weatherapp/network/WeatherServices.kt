package com.hanaahany.weatherapp.network

import com.hanaahany.weatherapp.model.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherServices:RemoteSource {
    @GET("onecall")
    suspend fun currentWeather(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("appid") appid:String ="5f40589c146e66738eacada978281f07"):Response<WeatherResponse>


}