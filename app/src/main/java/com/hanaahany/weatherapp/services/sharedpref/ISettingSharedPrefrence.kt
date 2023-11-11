package com.hanaahany.weatherapp.services.sharedpref

interface ISettingSharedPrefrence {

    fun writeStringSettings(key:String, value:String)
    fun readStringSettings(key:String):String

    fun writeFloatSettings(key:String, value:Float)
    fun readFloatSettings(key:String):Float




}