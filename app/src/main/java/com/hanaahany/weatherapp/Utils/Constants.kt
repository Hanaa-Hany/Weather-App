package com.hanaahany.weatherapp.Utils

import android.content.Context
import android.content.res.Configuration
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

object Constants {
    const val locationTag="LocationByGps"
    const val SETTING="Setting"

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

}