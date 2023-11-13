package com.hanaahany.weatherapp.Utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.location.Geocoder
import android.util.Log
import com.airbnb.lottie.LottieAnimationView
import com.hanaahany.weatherapp.R
import com.hanaahany.weatherapp.services.sharedpref.SettingSharedPrefrences
import java.text.SimpleDateFormat
import java.util.*

object Constants {
    const val CHANNEL_NAME= "weather"
    const val CHANNEL_DESCRIPTION = "weather notification"
    const val CHANNEL_ID = "123"
    const val NOTIFICATION_ID = 1
    const val ALARM_ITEM = "alarmItem"
    const val ALERT_DESCRIPTION = "alertDescription"
    const val ALERT = "alert"
    const val NOTIFICATION = "notification"
    const val DISABLE = "disable"


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
    const val FAV_Source = "FavouriteFragment"
    const val SET_Source = "SettingsFragment"
    const val SET_LAT = "lat"
    const val SET_LAN = "lan"



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
                .readStringSettings(UNIT) == "metric"
        )  return "${value} ℃ "
        else if (SettingSharedPrefrences.getInstance(context)
                .readStringSettings(UNIT) == "imperial"
        ) return "${value} ℉ "
        else return "${value} \u212A"
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
            return addresses[0].subAdminArea

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
            "10d" -> lottiView.setAnimation(R.raw.rain_day)
            "01d" -> lottiView.setAnimation(R.raw.sunny)
            "02d" -> lottiView.setAnimation(R.raw.few_clouds)
            "03d" -> lottiView.setAnimation(R.raw.clouds)
            "04d" -> lottiView.setAnimation(R.raw.clouds_04d)
            "09d" -> lottiView.setAnimation(R.raw.rain_day)
            "11d" -> lottiView.setAnimation(R.raw.thunder_11d)
            "13d" -> lottiView.setAnimation(R.raw.snowfall)
            "50d" -> lottiView.setAnimation(R.raw.mist)
            "01n" -> lottiView.setAnimation(R.raw.first_night)
            "02n" -> lottiView.setAnimation(R.raw._02n)
            "03n" -> lottiView.setAnimation(R.raw._03n)
            "04n" -> lottiView.setAnimation(R.raw.clouds_04d)


        }
    }

    fun formatHourMinuteToString(hour: Int, minute: Int): String {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minute)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        val dateFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
        return dateFormat.format(calendar.time)
    }
    fun formatLongToAnyString(dateTimeInMillis: Long, pattern: String): String {
        val resultFormat = SimpleDateFormat(pattern, Locale.getDefault())
        val date = Date(dateTimeInMillis)
        return resultFormat.format(date)
    }
    fun formatFromStringToLong(dateText: String, timeText: String): Long {
        val dateFormat = SimpleDateFormat("dd MMM yyyy hh:mm a", Locale.getDefault())
        val dateAndTime = "$dateText $timeText}"
        val date = dateFormat.parse(dateAndTime)
        return date?.time ?: -1
    }


}
