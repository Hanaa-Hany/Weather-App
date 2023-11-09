package com.hanaahany.weatherapp.data.source

import com.hanaahany.weatherapp.model.WeatherResponse
import com.hanaahany.weatherapp.network.RemoteSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeRemoteSource(private var weatherResponse: WeatherResponse):RemoteSource {

    override suspend fun makeNetworkCall(
        lat: Double,
        lon: Double,
        units: String,
        lang: String
    ): Flow<WeatherResponse> {
        return flowOf(weatherResponse)
    }
}