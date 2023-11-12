package com.hanaahany.weatherapp.services.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.hanaahany.weatherapp.Utils.Constants
import com.hanaahany.weatherapp.services.model.Alarm

class AlarmSchedular private constructor(context: Context):IAlarmSchedular {
    private val alarmManager: AlarmManager by lazy {
        context.getSystemService(AlarmManager::class.java)
    }

    companion object {
        private var instance: AlarmSchedular? = null

        fun getInstance(context: Context): AlarmSchedular =
            instance ?: synchronized(this) {
                instance ?: AlarmSchedular(context).also { instance = it }
            }
    }

    override fun startAlarm(item: Alarm, context: Context) {
        val intent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra(Constants.ALARM_ITEM, item)
        }
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            item.time,
            PendingIntent.getBroadcast(
                context,
                item.time.toInt(),
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )
    }

    override fun stopAlarm(item: Alarm, context: Context) {
        alarmManager.cancel(
            PendingIntent.getBroadcast(
                context,
                item.time.toInt(),
                Intent(context, AlarmReceiver::class.java),
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )
    }
}