package com.hanaahany.weatherapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "alarm_table")
data class Alarm(
    @PrimaryKey(autoGenerate = true)
    val time: Long,
    val kind: String,
    val latitude: Double,
    val longitude: Double,
    val zoneName: String = ""
): Serializable