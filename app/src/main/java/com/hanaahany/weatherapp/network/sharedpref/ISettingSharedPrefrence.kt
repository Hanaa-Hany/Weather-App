package com.hanaahany.weatherapp.network.sharedpref

interface ISettingSharedPrefrence {

    fun writeStringSettings(key:String, value:String)
    fun readStringSettings(key:String):String
    fun writeUnits(key:String,value:Float)
    fun readUnits(key:String):Float



}