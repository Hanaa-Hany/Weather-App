package com.hanaahany.weatherapp.dp

import android.content.Context
import com.hanaahany.weatherapp.model.Place
import com.hanaahany.weatherapp.model.WeatherResponse
import kotlinx.coroutines.flow.Flow

class LocalSource(context: Context):ILocalSource {
    private var locationDAO: LocationDAO


    init {
        val appDataBase: AppDataBase = AppDataBase.getInstance(context)
        locationDAO = appDataBase.getLocationDAO()

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

}