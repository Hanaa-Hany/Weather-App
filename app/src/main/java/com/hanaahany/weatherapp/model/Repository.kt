package com.hanaahany.weatherapp.model

import com.hanaahany.weatherapp.dp.LocalSource
import com.hanaahany.weatherapp.network.RemoteSource
import com.hanaahany.weatherapp.network.sharedpref.SettingSharedPrefrences
import kotlinx.coroutines.flow.Flow

class Repository private constructor(
    var remoteSource: RemoteSource,
    var settingSharedPrefrences: SettingSharedPrefrences,
    var localSource: LocalSource
) : RepositoryInterface {

    companion object {
        private var INSTANCE: Repository? = null
        fun getInstance(
            remoteSource: RemoteSource,
            settingSharedPrefrences: SettingSharedPrefrences,
            localSource: LocalSource
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

    override fun getLocationFromDB(): Flow<List<Place>> {
        return localSource.getLocationFromDB()
    }

    override suspend fun insertFavLocation(place: Place) {
        localSource.insertLocationToDB(place)
    }

    override suspend fun deleteLocationFromDB(place: Place) {
        localSource.deleteLocationFromDB(place)
    }


}