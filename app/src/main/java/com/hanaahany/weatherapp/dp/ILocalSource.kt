package com.hanaahany.weatherapp.dp

import com.hanaahany.weatherapp.model.Place
import kotlinx.coroutines.flow.Flow

interface ILocalSource {

    fun getLocationFromDB(): Flow<List<Place>>

    suspend fun insertLocationToDB(place: Place)

    suspend fun deleteLocationFromDB(place: Place)
}