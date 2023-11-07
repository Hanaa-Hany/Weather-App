package com.hanaahany.weatherapp.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hanaahany.weatherapp.Utils.ApiState
import com.hanaahany.weatherapp.model.RepositoryInterface
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel(private val _irepo: RepositoryInterface) : ViewModel() {

    private val _respone: MutableStateFlow<ApiState> =MutableStateFlow(ApiState.Loading)
    var respone: StateFlow<ApiState> = _respone

    fun getWeather(lat: Double, lon: Double,units:String,lang:String) {
        viewModelScope.launch {

            _irepo.makeNetworkCall(lat, lon,units,lang).catch {
                _respone.value=ApiState.Fail(it)
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


}