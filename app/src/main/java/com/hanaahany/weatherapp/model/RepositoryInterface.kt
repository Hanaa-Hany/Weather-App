package com.hanaahany.weatherapp.model

import kotlinx.coroutines.flow.Flow

interface RepositoryInterface {
    suspend fun makeNetworkCall( lat:Double, lon:Double,units:String,lang:String): Flow<WeatherResponse>
    suspend fun getCachedData( ): Flow<WeatherResponse>
    suspend fun  insertCachedWeather(weatherResponse: WeatherResponse)

    fun writeStringToSetting(key:String, value:String)
    fun readStringFromSetting(key:String):String
    fun writeFloatToSetting(key:String, value:Float)
    fun readFloatFromSetting(key:String):Float

    fun getFavLocation(): Flow<List<Place>>

    suspend fun insertFavLocation(place: Place)

    suspend fun deleteLocationFromDB(place: Place)


    suspend fun insertAlarm(alarmItem: Alarm)

    suspend fun deleteAlarm(alarmItem: Alarm)

    fun getAllAlarms(): Flow<List<Alarm>>

//    fun writeUnits(key:String,value:Float)
//    fun readUnits(key:String):Float

}