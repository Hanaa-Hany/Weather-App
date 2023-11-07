package com.hanaahany.weatherapp.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hanaahany.weatherapp.Utils.ApiState
import com.hanaahany.weatherapp.model.RepositoryInterface
import com.hanaahany.weatherapp.model.WeatherResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import retrofit2.Response

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
    fun writeLanguageToSetting(key:String,value:String){
        _irepo.writeLanguageToSetting(key,value)
    }
    fun readLanguageFromSetting(key:String):String{
       return _irepo.readLanguageFromSetting(key)
    }

}