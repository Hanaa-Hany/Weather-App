package com.hanaahany.weatherapp.dp

import com.hanaahany.weatherapp.model.Alarm
import com.hanaahany.weatherapp.model.Place
import com.hanaahany.weatherapp.model.WeatherResponse
import kotlinx.coroutines.flow.Flow

interface ILocalSource {

     fun getCachedWeather():Flow<WeatherResponse>
    suspend fun  insertCachedWeather(weatherResponse: WeatherResponse)
    fun getLocationFromDB(): Flow<List<Place>>

    suspend fun insertLocationToDB(place: Place)

    suspend fun deleteLocationFromDB(place: Place)

    suspend fun insertAlarm(alarmItem: Alarm)

    suspend fun deleteAlarm(alarmItem: Alarm)

    fun getAllAlarms(): Flow<List<Alarm>>
}