package com.hanaahany.weatherapp.Utils

import java.text.SimpleDateFormat
import java.util.*

object Constants {
    const val locationTag="LocationByGps"
    fun getTimeHour(value:Long): String {
        val timestamp: Long = value
        val date = Date(timestamp * 1000) // Convert seconds to milliseconds
        val dateFormat = SimpleDateFormat("h a", Locale.getDefault())
        val formattedDate = dateFormat.format(date)
        return formattedDate
    }
    fun getTime(value:Long): String {
        val timestamp: Long = value
        val date = Date(timestamp * 1000) // Convert seconds to milliseconds
        val dateFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
        val formattedDate = dateFormat.format(date)
        return formattedDate
    }
    fun getDate(value:Long): String {
        val timestamp: Long = value
        val date = Date(timestamp * 1000) // Convert seconds to milliseconds
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val formattedDate = dateFormat.format(date)
        return formattedDate
    }
    fun getDateDay(value:Long): String {
        val timestamp: Long = value
        val date = Date(timestamp * 1000) // Convert seconds to milliseconds
        val dateFormat = SimpleDateFormat("EEEE", Locale.getDefault())
        val formattedDate = dateFormat.format(date)
        return formattedDate
    }
}