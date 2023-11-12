package com.hanaahany.weatherapp.services.dp

import androidx.room.*
import com.hanaahany.weatherapp.services.model.Alarm
import com.hanaahany.weatherapp.services.model.Place
import com.hanaahany.weatherapp.services.model.WeatherResponse
import kotlinx.coroutines.flow.Flow



@Dao
interface LocationDAO {
  @Query("SELECT * FROM Place_Table")
   fun getLocationFromDB():Flow<List<Place>>
  @Insert(onConflict=OnConflictStrategy.REPLACE)
  suspend fun insertLocationToDB(place: Place)
  @Delete
  suspend fun deleteLocationFromDB(place: Place)

  @Insert(onConflict=OnConflictStrategy.REPLACE)
  suspend fun insertCachedData(weatherResponse: WeatherResponse)
  @Query("SELECT * FROM weather_table")
  fun getCachedData():Flow<WeatherResponse>

  @Insert(onConflict=OnConflictStrategy.REPLACE)
  suspend fun insertAlarm(alarmItem: Alarm)
  @Delete
  suspend fun deleteAlarm(alarmItem: Alarm)
  @Query("SELECT * FROM alarm_table")
  fun getAllAlarms(): Flow<List<Alarm>>


}