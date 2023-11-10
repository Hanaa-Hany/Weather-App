package com.hanaahany.weatherapp.Utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.*
import android.os.Looper
import android.provider.Settings
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import java.util.*
import kotlin.properties.Delegates

private const val MAP_PERMISSION_ID = 505

class LocationByGPS constructor(var context: Context) {

    private var mutableLocation: MutableLiveData<Pair<Double, Double>> = MutableLiveData()
    var fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    private lateinit var geocoder: Geocoder
    var location: LiveData<Pair<Double, Double>> = mutableLocation

    private var _address=MutableLiveData<MutableList<Address>>()
      var   addresses: LiveData<MutableList<Address>> = _address

    @SuppressLint("MissingPermission")
    fun getLastLocation() {
        if (Permission.checkPermission(context)) {
            if (Permission.isLocationIsEnabled(context)) {
                requestNewLocation()
            } else {
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                context.startActivity(intent)

            }
        } else {
            requestPermission()
        }

    }





    @SuppressLint("MissingPermission", "SuspiciousIndentation")
    private fun requestNewLocation() {
        val locationRequest = com.google.android.gms.location.LocationRequest()
        locationRequest.priority = LocationRequest.QUALITY_HIGH_ACCURACY
        locationRequest.interval = 0

        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallBack,
            Looper.myLooper()
        )
    }

    fun requestPermission() {
        ActivityCompat.requestPermissions(
            context as Activity, arrayOf(
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ), MAP_PERMISSION_ID
        )
    }

    private val locationCallBack: LocationCallback = object : LocationCallback() {
        @SuppressLint("SuspiciousIndentation")

        override fun onLocationResult(locationResult: LocationResult) {
            super.onLocationResult(locationResult)

            val lastLocation: Location = locationResult.lastLocation
            Log.i(Constants.locationTag, lastLocation.latitude.toString())

            stopLocationUpdate()
            mutableLocation.postValue(Pair(lastLocation.latitude, lastLocation.longitude))
            lat=lastLocation.latitude
            lang=lastLocation.longitude
            geocoder = Geocoder(context, Locale.getDefault())
            _address.postValue(geocoder.getFromLocation(lastLocation.latitude, lastLocation.longitude, 1)!!)
            Log.i(Constants.locationTag,lastLocation.latitude.toString()+"file")
            Log.i(Constants.locationTag,
                geocoder.getFromLocation(lastLocation.latitude, lastLocation.longitude, 1)!![0].toString())



    }}

    fun stopLocationUpdate() {
        fusedLocationClient.removeLocationUpdates(locationCallBack)
    }

    companion object{
         var lat by Delegates.notNull<Double>()
        var lang by Delegates.notNull<Double>()
    }


}
