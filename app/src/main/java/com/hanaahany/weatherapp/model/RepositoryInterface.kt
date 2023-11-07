package com.hanaahany.weatherapp.model

import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface RepositoryInterface {
    suspend fun makeNetworkCall( lat:Double, lon:Double,units:String,lang:String): Flow<WeatherResponse>
    fun writeLanguageToSetting(key:String,value:String)
    fun readLanguageFromSetting(key:String):String

}