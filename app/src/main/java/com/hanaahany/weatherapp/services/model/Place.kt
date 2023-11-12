package com.hanaahany.weatherapp.services.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "Place_Table")
data class Place(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var cityName: String,
    var latitude: Double,
    var longitude: Double,
    var city: String,
    var temp: Double,
    var date: String,
    var icon:String,
    var pressure:Int,
    var humidity:Int,
    var cloud:Int,
    var windSpeed: Double,
    var description: String,
    var hourly: List<HourlyWeather>,
    var daily: List<DailyWeather>
) : Serializable



