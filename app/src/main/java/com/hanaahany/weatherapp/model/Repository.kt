package com.hanaahany.weatherapp.model

import com.hanaahany.weatherapp.dp.ILocalSource
import com.hanaahany.weatherapp.services.network.RemoteSource
import com.hanaahany.weatherapp.services.sharedpref.ISettingSharedPrefrence
import kotlinx.coroutines.flow.Flow

class Repository private constructor(
    var remoteSource: RemoteSource,
    var settingSharedPrefrences: ISettingSharedPrefrence,
    var localSource: ILocalSource,

) : RepositoryInterface {

    companion object {
        private var INSTANCE: Repository? = null
        fun getInstance(
            remoteSource: RemoteSource,
            settingSharedPrefrences: ISettingSharedPrefrence,
            localSource: ILocalSource
        ): Repository {
            if (INSTANCE == null) {
                INSTANCE = Repository(remoteSource, settingSharedPrefrences,localSource)
            }
            return INSTANCE!!
        }
    }

    override suspend fun makeNetworkCall(
        lat: Double,
        lon: Double,
        units: String,
        lang: String
    ): Flow<WeatherResponse> {
        return remoteSource.makeNetworkCall(lat, lon, units, lang)
    }

    override suspend fun getCachedData(): Flow<WeatherResponse> {
        return localSource.getCachedWeather()
    }

    override suspend fun insertCachedWeather(weatherResponse: WeatherResponse) {
        localSource.insertCachedWeather(weatherResponse)
    }


    override fun writeStringToSetting(key: String, value: String) {
        settingSharedPrefrences.writeStringSettings(key, value)
    }

    override fun readStringFromSetting(key: String): String {
        return settingSharedPrefrences.readStringSettings(key)
    }

    override fun writeFloatToSetting(key: String, value: Float) {
        settingSharedPrefrences.writeFloatSettings(key, value)
    }

    override fun readFloatFromSetting(key: String): Float {
        return settingSharedPrefrences.readFloatSettings(key)
    }

    override fun getFavLocation(): Flow<List<Place>> {
        return localSource.getLocationFromDB()
    }

    override suspend fun insertFavLocation(place: Place) {
        localSource.insertLocationToDB(place)
    }

    override suspend fun deleteLocationFromDB(place: Place) {
        localSource.deleteLocationFromDB(place)
    }

    override suspend fun insertAlarm(alarmItem: Alarm) {
        localSource.insertAlarm(alarmItem)
    }

    override suspend fun deleteAlarm(alarmItem: Alarm) {
        localSource.deleteAlarm(alarmItem)
    }

    override fun getAllAlarms(): Flow<List<Alarm>> {
        return localSource.getAllAlarms()
    }


}