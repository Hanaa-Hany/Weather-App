package com.hanaahany.weatherapp.dp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.hanaahany.weatherapp.model.Place
import com.hanaahany.weatherapp.model.WeatherResponse


@Database(entities = arrayOf(Place::class), version = 1)
@TypeConverters(ConverterDB::class)
abstract class AppDataBase: RoomDatabase() {
    abstract fun getLocationDAO(): LocationDAO
    companion object{
        @Volatile
        var INSTANCE: AppDataBase?=null
        fun getInstance(context: Context):AppDataBase {
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