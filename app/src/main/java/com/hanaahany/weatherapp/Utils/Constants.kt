package com.hanaahany.weatherapp.Utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.location.Geocoder
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewModelScope
import com.hanaahany.weatherapp.model.WeatherResponse
import com.hanaahany.weatherapp.network.sharedpref.SettingSharedPrefrences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

object Constants {
    const val locationTag="LocationByGps"
    const val SETTING="Setting"
    const val LANGUAGE="languageFile"
    const val UNIT="unitFile"
    const val WIND_SPEED="unitFile"
    const val LOCATION="locationFile"
    const val GPS="gpsFile"
    const val MAP="mapFile"
    const val LAT="latitude"
    const val LAN="longitude"








    fun getTimeHour(value:Long,lang:String): String {
        val timestamp: Long = value
        val date = Date(timestamp * 1000) // Convert seconds to milliseconds
        val dateFormat = SimpleDateFormat("h a",Locale(lang))
        val formattedDate = dateFormat.format(date)
        return formattedDate
    }
    fun getTime(value:Long,lang:String): String {
        val timestamp: Long = value
        val date = Date(timestamp * 1000) // Convert seconds to milliseconds
        val dateFormat = SimpleDateFormat("hh:mm a",Locale(lang))
        val formattedDate = dateFormat.format(date)
        return formattedDate
    }
    fun getDate(value:Long,lang:String): String {
        val timestamp: Long = value
        val date = Date(timestamp * 1000) // Convert seconds to milliseconds
        val dateFormat = SimpleDateFormat("yyyy-MM-dd",Locale(lang))
        val formattedDate = dateFormat.format(date)
        return formattedDate
    }
    fun getDateDay(value:Long,lang:String): String {
        val timestamp: Long = value
        val date = Date(timestamp * 1000) // Convert seconds to milliseconds
        val dateFormat = SimpleDateFormat("EEEE", Locale(lang))
        val formattedDate = dateFormat.format(date)
        return formattedDate
    }
    fun changeLanguage(language: String,context: Context) {

        val newLocale = Locale(language)
        Locale.setDefault(newLocale)
        val configuration = Configuration()
        configuration.setLocale(newLocale)
        context.resources.updateConfiguration(configuration, context.resources.displayMetrics)
    }
    fun writeDegree(context:Context,value:String):String{
        if (SettingSharedPrefrences.getInstance(context)
                .readStringSettings(UNIT) == "standard"
        ) return "${value} \u212A"
        else if (SettingSharedPrefrences.getInstance(context)
                .readStringSettings(UNIT) == "metric"
        ) return "${value} \u2103"
        else return "${value} \u2109"
    }
    fun windSpeed(context:Context,value:String):String{
       if (SettingSharedPrefrences.getInstance(context)
                .readStringSettings(WIND_SPEED) == "metric"
        ) return "${value} m/s "
        else return "${value} miles "
    }

    @SuppressLint("SetTextI18n")
    fun setLocationNameByGeoCoder(weatherResponse: WeatherResponse, context: Context): String {
        var latit=0.0
        var langi=0.0
        LocationByGPS(context).location.observe(context as LifecycleOwner){
            latit=it.first
            langi=it.second
        }
        try {
            val x =
                Geocoder(context).getFromLocation(
                        latit,langi,1

                )
            Log.i(locationTag,"ff"+weatherResponse.lat.toString())
            return if (x != null && x[0].subAdminArea != null) {
                Log.i(locationTag, x[0].subAdminArea.toString())
                x[0].subAdminArea


            } else {
                weatherResponse.timezone
            }
        } catch (e: Exception) {
            return weatherResponse.timezone
        }
    }
}

