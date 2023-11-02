package com.hanaahany.weatherapp.model

import com.hanaahany.weatherapp.network.RemoteSource
import retrofit2.Response

class Repository private constructor(var remoteSource: RemoteSource):RepositoryInterface{

    companion object{
        private var INSTANCE:Repository?=null
        fun getInstance(remoteSource: RemoteSource):Repository {
            if (INSTANCE == null) {
                INSTANCE = Repository(remoteSource)
            }
            return INSTANCE!!
        }
    }
    override suspend fun makeNetworkCall(lat: Double, lon: Double): List<WeatherResponse> {
        return remoteSource.makeNetworkCall(lat,lon)
    }
}