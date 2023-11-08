package com.hanaahany.weatherapp.home.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hanaahany.weatherapp.Utils.ApiState
import com.hanaahany.weatherapp.Utils.Constants
import com.hanaahany.weatherapp.model.Place
import com.hanaahany.weatherapp.model.RepositoryInterface
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class HomeViewModel(private val _irepo: RepositoryInterface) : ViewModel() {

    private val _respone: MutableStateFlow<ApiState> =MutableStateFlow(ApiState.Loading)
    var respone: StateFlow<ApiState> = _respone

    private val _favLocation: MutableStateFlow<List<Place>> =MutableStateFlow(emptyList())
    var favLocation: StateFlow<List<Place>> = _favLocation


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
            _irepo.insertFavLocation(place)
        }
    }
    fun getFavLocation(){
        viewModelScope.launch {
             _irepo.getLocationFromDB().collect {
                 Log.i(Constants.FAV_TAG,it.size.toString())
                 _favLocation.value=it
             }
        }
    }



}