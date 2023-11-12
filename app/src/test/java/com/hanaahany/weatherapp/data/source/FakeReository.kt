package com.hanaahany.weatherapp.data.source

import com.hanaahany.weatherapp.services.model.Alarm
import com.hanaahany.weatherapp.services.model.Place
import com.hanaahany.weatherapp.services.model.RepositoryInterface
import com.hanaahany.weatherapp.services.model.WeatherResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeReository(private val places: MutableList<Place> = mutableListOf(),
                    private val weather:WeatherResponse ,
                    private val alarm: MutableList<Alarm> = mutableListOf(),
                    private var read: String,
                    private var readFloat: Float
) : RepositoryInterface {
    override suspend fun makeNetworkCall(
        lat: Double,
        lon: Double,
        units: String,
        lang: String
    ): Flow<WeatherResponse> {
        return flowOf(weather)
    }
    override fun readFloatFromSetting(key: String): Float {
        return readFloat
    }

    override fun getFavLocation(): Flow<List<Place>> {
        return flowOf(places)
    }

    override suspend fun insertFavLocation(place: Place) {
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

    override suspend fun getCachedData(): Flow<WeatherResponse> {
        TODO()
        //return flowOf(weather.get(0))
    }

    override suspend fun insertCachedWeather(weatherResponse: WeatherResponse) {
        //weather.add(weatherResponse)
    }

    override fun writeStringToSetting(key: String, value: String) {
        read=value

    }

    override fun readStringFromSetting(key: String): String {
        return read

    }

    override fun writeFloatToSetting(key: String, value: Float) {
        readFloat=value

    }


}