package com.hanaahany.weatherapp.Utils

import com.hanaahany.weatherapp.model.WeatherResponse

sealed class ApiState{
    class Success(val date: WeatherResponse):ApiState()
    class Fail(val message:Throwable):ApiState()
    object Loading:ApiState()
}