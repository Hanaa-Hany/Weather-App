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



