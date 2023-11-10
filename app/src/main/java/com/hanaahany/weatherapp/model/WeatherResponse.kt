package com.hanaahany.weatherapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather_table")
data class WeatherResponse(
    @PrimaryKey(autoGenerate = true)
    var id:Int=0,
    var lat: Double = 0.0,
    var long: Double = 0.0,
    var timezone: String = "",
    var current: CurrentWeather,
    var hourly: List<HourlyWeather>,
    var daily: List<DailyWeather>
){
    constructor() :this (0,0.0, 0.0, "", CurrentWeather(0,0.0,0.0,0,0,0,0.0, emptyList()), emptyList(), emptyList())
}
