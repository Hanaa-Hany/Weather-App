package com.hanaahany.weatherapp.services.dp

import android.content.Context
import com.hanaahany.weatherapp.services.model.Alarm
import com.hanaahany.weatherapp.services.model.Place
import com.hanaahany.weatherapp.services.model.WeatherResponse
import kotlinx.coroutines.flow.Flow

class LocalSource(context: Context): ILocalSource {
    private var locationDAO: LocationDAO


    init {
        val appDataBase: AppDataBase = AppDataBase.getInstance(context)
        locationDAO = appDataBase.getLocationDAO()

    }

    override fun getCachedWeather(): Flow<WeatherResponse> {
        return locationDAO.getCachedData()
    }

    override suspend fun insertCachedWeather(weatherResponse: WeatherResponse) {
        locationDAO.insertCachedData(weatherResponse)
    }

    override fun getLocationFromDB(): Flow<List<Place>> {
        return locationDAO.getLocationFromDB()
    }

    override suspend fun insertLocationToDB(place: Place) {
        locationDAO.insertLocationToDB(place)
    }

    override suspend fun deleteLocationFromDB(place: Place) {
        locationDAO.deleteLocationFromDB(place)
    }

    override suspend fun insertAlarm(alarmItem: Alarm) {
        locationDAO.insertAlarm(alarmItem)
    }

    override suspend fun deleteAlarm(alarmItem: Alarm) {
        locationDAO.deleteAlarm(alarmItem)
    }

    override fun getAllAlarms(): Flow<List<Alarm>> {
        return locationDAO.getAllAlarms()
    }

}