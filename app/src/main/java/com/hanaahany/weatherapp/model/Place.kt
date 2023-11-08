package com.hanaahany.weatherapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "Place_Table")
data class Place(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var cityName: String,
    var latitude: Double,
    var longitude: Double,
    var city: String,
    var temp: Double,
    var date: String,
    var icon:String
) : Serializable

