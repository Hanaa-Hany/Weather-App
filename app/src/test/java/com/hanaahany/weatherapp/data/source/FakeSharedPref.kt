package com.hanaahany.weatherapp.data.source

import com.hanaahany.weatherapp.network.sharedpref.ISettingSharedPrefrence

class FakeSharedPref( private var stringValue: String,
                      private var floatValue: Float):ISettingSharedPrefrence {
    override fun writeStringSettings(key: String, value: String) {
        stringValue=value
    }

    override fun readStringSettings(key: String): String {
        return stringValue
    }

    override fun writeFloatSettings(key: String, value: Float) {
        floatValue=value
    }

    override fun readFloatSettings(key: String): Float {
        return floatValue
    }
}