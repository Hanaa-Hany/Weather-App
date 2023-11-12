package com.hanaahany.weatherapp.services.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather_table")
data class WeatherResponse(
    @PrimaryKey(autoGenerate = true)
    var id:Int=0,
    @ColumnInfo("Lat")
    var lat: Double = 0.0,
    @ColumnInfo("long")
    var lon: Double = 0.0,
    @ColumnInfo("timezone")
    var timezone: String = "",
    @ColumnInfo("current")
    var current: CurrentWeather,
    @ColumnInfo("hourly")
    var hourly: List<HourlyWeather>,
    @ColumnInfo("daily")
    var daily: List<DailyWeather>,
    @ColumnInfo("alerts")
    var alerts:List<Alert>
){
    constructor() :this (0,0.0, 0.0, "", CurrentWeather(0,0.0,0.0,0,0,0,0.0, emptyList()), emptyList(), emptyList(),
        emptyList()
    )
}
data class CurrentWeather(
    var dt: Long,
    var temp: Double,
    var feels_like: Double,
    var pressure: Int,
    var humidity: Int,
    var clouds: Int,
    var wind_speed: Double,
    var weather: List<WeatherData>
)

data class WeatherData(var main: String, var description: String, var icon: String)
data class HourlyWeather(
    var dt: Long,
    var temp: Double,
    var feels_like: Double,
    var pressure: Int,
    var humidity: Int,
    var clouds: Int,
    var wind_speed: Double,
    var weather: List<WeatherData>
)

data class DailyWeather(
    var dt: Long,
    var temp: Temp,
    var feels_like: FeelsLike,
    var pressure: Int,
    var humidity: Int,
    var clouds: Int,
    var wind_speed: Double,
    var weather: List<WeatherData>
)

data class Temp(
    var day: Double,
    var min: Double,
    var max: Double,
    var night: Double,
    var eve: Double,
    var morn: Double
)

data class FeelsLike(var day: Double, var night: Double, var eve: Double, var morn: Double)
data class Alert(var sender_name:String,var event:String,var start:String,var end:Int,var description:String,var tags:List<String>)


