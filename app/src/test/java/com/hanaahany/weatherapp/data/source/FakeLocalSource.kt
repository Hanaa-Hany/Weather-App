package com.hanaahany.weatherapp.data.source

import com.hanaahany.weatherapp.services.dp.ILocalSource
import com.hanaahany.weatherapp.services.model.Alarm
import com.hanaahany.weatherapp.services.model.Place
import com.hanaahany.weatherapp.services.model.WeatherResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf


class FakeLocalSource(
    private val places: MutableList<Place> = mutableListOf(),
    private val weather: MutableList<WeatherResponse> = mutableListOf(),
    private val alarm: MutableList<Alarm> = mutableListOf()
) : ILocalSource {
    override fun getCachedWeather(): Flow<WeatherResponse> {
        return flowOf(weather[0])
    }

    override suspend fun insertCachedWeather(weatherResponse: WeatherResponse) {
        weather.add(weatherResponse)
    }


    override fun getLocationFromDB(): Flow<List<Place>> {
        return flowOf(places)
    }

    override suspend fun insertLocationToDB(place: Place) {
        places.add(place)
    }

    override suspend fun deleteLocationFromDB(place: Place) {
        places.remove(place)
    }

    override suspend fun insertAlarm(alarmItem: Alarm) {

        alarm.add(alarmItem)
    }

    override suspend fun deleteAlarm(alarmItem: Alarm) {
        alarm.remove(alarmItem)
    }

    override fun getAllAlarms(): Flow<List<Alarm>> {
        return flowOf(alarm)
    }
}