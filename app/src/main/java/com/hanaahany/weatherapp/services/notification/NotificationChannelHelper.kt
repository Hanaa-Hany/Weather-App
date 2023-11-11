package com.example.noaa.services.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.hanaahany.weatherapp.MainActivity
import com.hanaahany.weatherapp.R

import com.hanaahany.weatherapp.Utils.Constants

object NotificationChannelHelper {

    private var channelIsCreated = false

    @RequiresApi(Build.VERSION_CODES.O)
    fun createNotificationChannel(context: Context) {
        if (!channelIsCreated) {
            // Create the NotificationChannel.
            val name = Constants.CHANNEL_NAME
            val descriptionText = Constants.CHANNEL_DESCRIPTION
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val myChannel = NotificationChannel(Constants.CHANNEL_ID, name, importance)
            myChannel.description = descriptionText
            val notificationManager =
                context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(myChannel)
            channelIsCreated = true
        }
    }

    fun createNotification(context: Context, description: String, zoneName: String): NotificationCompat.Builder {
        val pendingIntent = createPendingIntent(context)
        val builder = NotificationCompat.Builder(context, Constants.CHANNEL_ID)
        builder.setSmallIcon(R.drawable.ic_notification)
        builder.setContentTitle("Weather")
        builder.setContentText("$description in $zoneName")
        builder.setStyle(
            NotificationCompat.BigTextStyle()
                .bigText("$description in $zoneName")
        )
        builder.priority = NotificationCompat.PRIORITY_DEFAULT
        builder.setContentIntent(pendingIntent)
        builder.setAutoCancel(true)

        return builder
    }

    private fun createPendingIntent(context: Context): PendingIntent {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        return PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)
    }
}