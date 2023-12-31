package com.hanaahany.weatherapp.services.dp

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.hanaahany.weatherapp.services.model.Alert
import com.hanaahany.weatherapp.services.model.CurrentWeather
import com.hanaahany.weatherapp.services.model.DailyWeather
import com.hanaahany.weatherapp.services.model.HourlyWeather

class ConverterDB {
    @TypeConverter
    fun fromCurrentToString(current: CurrentWeather): String {
        return Gson().toJson(current)
    }

    @TypeConverter
    fun fromStringToCurrent(stringCurrent: String): CurrentWeather {
        return Gson().fromJson(stringCurrent, CurrentWeather::class.java)
    }

    @TypeConverter
    fun fromDailyToString(daily: List<DailyWeather>): String {
        return Gson().toJson(daily)
    }

    @TypeConverter
    fun fromStringToDaily(stringDaily: String): List<DailyWeather> {
        return Gson().fromJson(stringDaily, object : TypeToken<List<DailyWeather>>() {}.type)
    }


    @TypeConverter
    fun fromHourlyToString(hourly: List<HourlyWeather>): String {
        return Gson().toJson(hourly)
    }

    @TypeConverter
    fun fromStringToHourly(stringHourly: String): List<HourlyWeather> {
        return Gson().fromJson(stringHourly, object : TypeToken<List<HourlyWeather>>() {}.type)
    }

    @TypeConverter
    fun fromAlertToString(alert: List<Alert>): String {
        return Gson().toJson(alert)
    }

    @TypeConverter
    fun fromStringToAlert(stringAlert: String): List<Alert> {
        return Gson().fromJson(stringAlert, object : TypeToken<List<Alert>>() {}.type)
    }



}