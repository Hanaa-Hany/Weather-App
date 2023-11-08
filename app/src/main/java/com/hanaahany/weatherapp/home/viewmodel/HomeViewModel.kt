package com.hanaahany.weatherapp.home.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.hanaahany.weatherapp.Utils.ApiState
import com.hanaahany.weatherapp.Utils.Constants
import com.hanaahany.weatherapp.Utils.LocationByGPS
import com.hanaahany.weatherapp.model.Place
import com.hanaahany.weatherapp.model.RepositoryInterface
import com.hanaahany.weatherapp.model.WeatherResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel(private val _irepo: RepositoryInterface) : ViewModel() {

    private val _respone: MutableStateFlow<ApiState> =MutableStateFlow(ApiState.Loading)
    var respone: StateFlow<ApiState> = _respone


    fun getWeather(lat: Double, lon: Double,units:String="metric",lang:String="en") {
        viewModelScope.launch {

            _irepo.makeNetworkCall(lat, lon,units,lang).catch {
                _respone.value=ApiState.Fail(it.message.toString())
            }.collect{
                _respone.value=ApiState.Success(it)
            }


        }

    }
    fun writeStringToSetting(key:String, value:String){
        _irepo.writeStringToSetting(key,value)
    }
    fun readStringFromSetting(key:String):String{
       return _irepo.readStringFromSetting(key)
    }

    fun writeFloatToSetting(key:String, value:Float){
        _irepo.writeFloatToSetting(key,value)
    }
    fun readFloatFromSetting(key:String):Float{
        return _irepo.readFloatFromSetting(key)
    }

    fun insertFavLocation(place: Place){
        viewModelScope.launch {
            _irepo.insertLocationToDB(place)
        }
    }


}