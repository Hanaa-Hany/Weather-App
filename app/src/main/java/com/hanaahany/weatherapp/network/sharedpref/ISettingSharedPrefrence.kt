package com.hanaahany.weatherapp.network.sharedpref

interface ISettingSharedPrefrence {

    fun writeLanguage(key:String,value:String)
    fun readLanguage(key:String):String

}