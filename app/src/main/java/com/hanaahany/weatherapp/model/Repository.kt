package com.hanaahany.weatherapp.model

import com.hanaahany.weatherapp.network.RemoteSource
import com.hanaahany.weatherapp.network.sharedpref.SettingSharedPrefrences
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class Repository private constructor(var remoteSource: RemoteSource,var settingSharedPrefrences: SettingSharedPrefrences):RepositoryInterface{

    companion object{
        private var INSTANCE:Repository?=null
        fun getInstance(remoteSource: RemoteSource,settingSharedPrefrences: SettingSharedPrefrences):Repository {
            if (INSTANCE == null) {
                INSTANCE = Repository(remoteSource,settingSharedPrefrences)
            }
            return INSTANCE!!
        }
    }
    override suspend fun makeNetworkCall(lat: Double, lon: Double,units:String,lang:String): Flow<WeatherResponse> {
        return remoteSource.makeNetworkCall(lat,lon,units,lang)
    }

    override fun writeLanguageToSetting(key: String, value: String) {
        settingSharedPrefrences.writeLanguage(key,value)
    }

    override fun readLanguageFromSetting(key: String): String {
        return settingSharedPrefrences.readLanguage(key)
    }


}