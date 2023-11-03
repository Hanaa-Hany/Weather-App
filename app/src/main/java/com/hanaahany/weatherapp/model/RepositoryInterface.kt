package com.hanaahany.weatherapp.model

import retrofit2.Response

interface RepositoryInterface {
    suspend fun makeNetworkCall( lat:Double, lon:Double):Response<WeatherResponse>

}