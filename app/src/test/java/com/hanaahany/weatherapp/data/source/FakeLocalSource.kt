package com.hanaahany.weatherapp.data.source

import com.hanaahany.weatherapp.services.dp.ILocalSource
import com.hanaahany.weatherapp.services.model.Place
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf


class FakeLocalSource(  private val places: MutableList<Place> = mutableListOf()): ILocalSource {


    override fun getLocationFromDB(): Flow<List<Place>> {
        return flowOf(places)
    }

    override suspend fun insertLocationToDB(place: Place) {
        places.add(place)
    }

    override suspend fun deleteLocationFromDB(place: Place) {
       places.remove(place)
    }
}