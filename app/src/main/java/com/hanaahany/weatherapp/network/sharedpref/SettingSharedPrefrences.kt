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
    override fun writeLanguage(key: String, value: String) {
        sharedPreferences.edit().putString(key,value).apply()
    }

    override fun readLanguage(key: String):String {
        var value:String
        sharedPreferences.getString(key,"en").let {
            value=it?:"en"
        }
        return value

    }


}