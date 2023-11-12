package com.hanaahany.weatherapp.data.source

import com.hanaahany.weatherapp.services.model.Place
import com.hanaahany.weatherapp.services.model.RepositoryInterface
import com.hanaahany.weatherapp.services.model.WeatherResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeReository(private val places: MutableList<Place> = mutableListOf(),
                    private var weather: WeatherResponse,
                    private var stringReadValue: String
) : RepositoryInterface {
    override suspend fun makeNetworkCall(
        lat: Double,
        lon: Double,
        units: String,
        lang: String
    ): Flow<WeatherResponse> {
        return flowOf(weather)
    }

    override fun writeStringToSetting(key: String, value: String) {
        TODO()

    }

    override fun readStringFromSetting(key: String): String {
        TODO()

    }

    override fun writeFloatToSetting(key: String, value: Float) {
        TODO()

    }

    override fun readFloatFromSetting(key: String): Float {
        TODO()
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
}