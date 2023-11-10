package com.hanaahany.weatherapp.dp

import androidx.room.*
import com.hanaahany.weatherapp.model.Place
import com.hanaahany.weatherapp.model.WeatherResponse
import kotlinx.coroutines.flow.Flow



@Dao
interface LocationDAO {
  @Query("SELECT * FROM Place_Table")
   fun getLocationFromDB():Flow<List<Place>>
  @Insert(onConflict=OnConflictStrategy.REPLACE)
  suspend fun insertLocationToDB(place:Place)
  @Delete
  suspend fun deleteLocationFromDB(place: Place)
  @Insert(onConflict=OnConflictStrategy.REPLACE)
  suspend fun insertCachedData(weatherResponse: WeatherResponse)
  @Query("SELECT * FROM weather_table")
  fun getCachedData():Flow<WeatherResponse>
}