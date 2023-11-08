package com.hanaahany.weatherapp.Utils

interface IPermission {
    fun requestPermission()
    fun checkPermission(): Boolean
    fun isPermissionEnabled(): Boolean
}