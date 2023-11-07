package com.hanaahany.weatherapp.network.sharedpref

interface ISettingSharedPrefrence {

    fun writeStringSettings(key:String, value:String)
    fun readStringSettings(key:String):String




}