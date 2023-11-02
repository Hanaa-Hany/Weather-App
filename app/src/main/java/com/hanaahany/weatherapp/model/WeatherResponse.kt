package com.hanaahany.weatherapp.model

data class WeatherResponse(var lat:Double,var long:Double,var timezone:String,
var current:CurrentWeather,var hourly:List<HourlyWeather>,var daily:List<DailyWeather>)

data class CurrentWeather(var temp:Double,var feels_like:Double,var pressure:Int,var humidity:Int,var clouds:Int,var wind_speed:Double,
var weather:WeatherData)
data class WeatherData(var main:String,var description:String,var icon:String)
data class HourlyWeather(var temp:Double,var feels_like:Double,var pressure:Int,var humidity:Int,var clouds:Int,var wind_speed:Double,
                         var weather:WeatherData)
data class DailyWeather(var temp:Temp,var feels_like:FeelsLike,var pressure:Int,var humidity:Int,var clouds:Int,var wind_speed:Double,
                         var weather:WeatherData)
data class Temp(var day:Double,var min:Double,var max:Double,var night:Double,var eve:Double,var morn:Double)
data class FeelsLike(var day:Double,var night:Double,var eve:Double,var morn:Double)



