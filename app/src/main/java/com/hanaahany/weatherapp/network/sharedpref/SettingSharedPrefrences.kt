package com.hanaahany.weatherapp.network.sharedpref

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import com.hanaahany.weatherapp.Utils.Constants

class SettingSharedPrefrences(context:Context):ISettingSharedPrefrence {


    private val sharedPreferences: SharedPreferences by lazy {
        context.getSharedPreferences(Constants.SETTING, AppCompatActivity.MODE_PRIVATE)
    }

    companion object {
        private var instance: SettingSharedPrefrences? = null

        fun getInstance(context: Context): SettingSharedPrefrences {
            return instance ?: synchronized(this) {
                instance ?: SettingSharedPrefrences(context).also { instance = it }
            }
        }
    }
    override fun writeStringSettings(key: String, value: String) {
        sharedPreferences.edit().putString(key,value).apply()
    }

    override fun readStringSettings(key: String):String {
        var value:String
        sharedPreferences.getString(key,"en").let {
            value=it?:"en"
        }
        return value

    }

    override fun writeUnits(key: String, value: Float) {
        sharedPreferences.edit().putFloat(key,value).apply()
    }

    override fun readUnits(key: String): Float {
        var value:Float
        sharedPreferences.getFloat(key,0f).let {
            value=it
        }
        return value
    }


}