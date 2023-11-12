package com.hanaahany.weatherapp.services.dp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.hanaahany.weatherapp.services.model.Alarm
import com.hanaahany.weatherapp.services.model.Place
import com.hanaahany.weatherapp.services.model.WeatherResponse


@Database(entities = arrayOf(Place::class, WeatherResponse::class, Alarm::class), version = 1)
@TypeConverters(ConverterDB::class)
abstract class AppDataBase: RoomDatabase() {
    abstract fun getLocationDAO(): LocationDAO
    companion object{
        @Volatile
        var INSTANCE: AppDataBase?=null
        fun getInstance(context: Context): AppDataBase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDataBase::class.java, "locationDB"
                ).build()
                INSTANCE = instance
                return instance
            }

        }
    }
}