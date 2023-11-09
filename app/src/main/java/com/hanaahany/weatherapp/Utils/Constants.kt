package com.hanaahany.weatherapp.Utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.location.Geocoder
import android.util.Log
import com.airbnb.lottie.LottieAnimationView
import com.hanaahany.weatherapp.R
import com.hanaahany.weatherapp.network.sharedpref.SettingSharedPrefrences
import java.text.SimpleDateFormat
import java.util.*

object Constants {
    const val locationTag = "LocationByGps"
    const val SETTING = "Setting"
    const val LANGUAGE = "languageFile"
    const val UNIT = "unitFile"
    const val WIND_SPEED = "unitFile"
    const val LOCATION = "locationFile"
    const val GPS = "gpsFile"
    const val MAP = "mapFile"
    const val LAT = "latitude"
    const val LAN = "longitude"
    const val FAV_TAG = "FavFragment"


    fun getTimeHour(value: Long, lang: String): String {
        val timestamp: Long = value
        val date = Date(timestamp * 1000) // Convert seconds to milliseconds
        val dateFormat = SimpleDateFormat("h a", Locale(lang))
        val formattedDate = dateFormat.format(date)
        return formattedDate
    }

    fun getTime(value: Long, lang: String): String {
        val timestamp: Long = value
        val date = Date(timestamp * 1000) // Convert seconds to milliseconds
        val dateFormat = SimpleDateFormat("hh:mm a", Locale(lang))
        val formattedDate = dateFormat.format(date)
        return formattedDate
    }

    fun getDate(value: Long, lang: String): String {
        val timestamp: Long = value
        val date = Date(timestamp * 1000) // Convert seconds to milliseconds
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale(lang))
        val formattedDate = dateFormat.format(date)
        return formattedDate
    }

    fun getDateDay(value: Long, lang: String): String {
        val timestamp: Long = value
        val date = Date(timestamp * 1000) // Convert seconds to milliseconds
        val dateFormat = SimpleDateFormat("EEEE", Locale(lang))
        val formattedDate = dateFormat.format(date)
        return formattedDate
    }

    fun changeLanguage(language: String, context: Context) {

        val newLocale = Locale(language)
        Locale.setDefault(newLocale)
        val configuration = Configuration()
        configuration.setLocale(newLocale)
        context.resources.updateConfiguration(configuration, context.resources.displayMetrics)
    }

    fun writeDegree(context: Context, value: String): String {
        if (SettingSharedPrefrences.getInstance(context)
                .readStringSettings(UNIT) == "standard"
        ) return "${value} \u212A"
        else if (SettingSharedPrefrences.getInstance(context)
                .readStringSettings(UNIT) == "metric"
        ) return "${value} \u2103"
        else return "${value} \u2109"
    }

    fun windSpeed(context: Context, value: String): String {
        if (SettingSharedPrefrences.getInstance(context)
                .readStringSettings(WIND_SPEED) == "metric"
        ) return "${value} m/s "
        else return "${value} miles "
    }

    @SuppressLint("SetTextI18n")
    fun setLocationNameByGeoCoder(context: Context, latitude: Double, longitude: Double): String {

        val geocoder = Geocoder(context, Locale.getDefault())
        val addresses = geocoder.getFromLocation(latitude, longitude, 1)
        if (addresses != null && addresses[0].locality != null) {
            Log.i(locationTag, addresses[0].locality + "Fun")
            return addresses[0].locality

        } else {
            return "Unknown City"
        }
    }

    @SuppressLint("SetTextI18n")
    fun setCityNameByGeoCoder(context: Context, latitude: Double, longitude: Double): String {

        val geocoder = Geocoder(context, Locale.getDefault())
        val addresses = geocoder.getFromLocation(latitude, longitude, 1)
        if (addresses != null && addresses[0].adminArea != null) {
            Log.i(locationTag, addresses[0].adminArea + "Fun")
            return addresses[0].adminArea

        } else {
            return "Unknown City"
        }
    }

    fun setIcon(src: String, lottiView: LottieAnimationView) {
        when (src) {
            "https://openweathermap.org/img/wn/10d@2x.png" -> lottiView.setAnimation(R.raw.rain_day)
            "https://openweathermap.org/img/wn/01d@2x.png" -> lottiView.setAnimation(R.raw.sunny)
            "https://openweathermap.org/img/wn/02d@2x.png" -> lottiView.setAnimation(R.raw.few_clouds)
            "https://openweathermap.org/img/wn/03d@2x.png" -> lottiView.setAnimation(R.raw.clouds)
            "https://openweathermap.org/img/wn/04d@2x.png" -> lottiView.setAnimation(R.raw.broken_clouds)
            "https://openweathermap.org/img/wn/09d@2x.png" -> lottiView.setAnimation(R.raw.rain_day)
            "https://openweathermap.org/img/wn/11d@2x.png" -> lottiView.setAnimation(R.raw.thunder)
            "https://openweathermap.org/img/wn/13d@2x.png" -> lottiView.setAnimation(R.raw.snowfall)
            "https://openweathermap.org/img/wn/50d@2x.png" -> lottiView.setAnimation(R.raw.mist)
            "https://openweathermap.org/img/wn/01n@2x.png" -> lottiView.setAnimation(R.raw.first_night)
            "https://openweathermap.org/img/wn/02n@2x.png" -> lottiView.setAnimation(R.raw.snowfall)
            "https://openweathermap.org/img/wn/03n@2x.png" -> lottiView.setAnimation(R.raw.mist)
            "https://openweathermap.org/img/wn/04n@2x.png" -> lottiView.setAnimation(R.raw.first_night)


        }
    }


}
