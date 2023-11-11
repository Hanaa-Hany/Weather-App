package com.hanaahany.weatherapp.services.alarm

import android.content.Context
import com.hanaahany.weatherapp.model.Alarm

interface IAlarmSchedular {

    fun startAlarm(item: Alarm, context: Context)
    fun stopAlarm(item: Alarm, context: Context)
}