package com.hanaahany.weatherapp.model

import kotlinx.coroutines.flow.Flow

interface RepositoryInterface {
    suspend fun makeNetworkCall( lat:Double, lon:Double,units:String,lang:String): Flow<WeatherResponse>
    fun writeStringToSetting(key:String, value:String)
    fun readStringFromSetting(key:String):String
//    fun writeUnits(key:String,value:Float)
//    fun readUnits(key:String):Float

}