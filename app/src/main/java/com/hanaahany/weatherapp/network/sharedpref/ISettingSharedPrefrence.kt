package com.hanaahany.weatherapp.network.sharedpref

interface ISettingSharedPrefrence {

    fun writeStringSettings(key:String, value:String)
    fun readStringSettings(key:String):String

    fun writeFloatSettings(key:String, value:Float)
    fun readFloatSettings(key:String):Float




}