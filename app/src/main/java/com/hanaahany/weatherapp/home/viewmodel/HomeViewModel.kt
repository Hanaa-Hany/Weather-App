package com.hanaahany.weatherapp.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hanaahany.weatherapp.model.RepositoryInterface
import com.hanaahany.weatherapp.model.WeatherResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(private val _irepo: RepositoryInterface):ViewModel() {

    private val _weatherList= MutableLiveData<List<WeatherResponse>>()
    val weatherList: LiveData<List<WeatherResponse>> =_weatherList
    fun getWeather(lat:Double, lon:Double){
        viewModelScope.launch(Dispatchers.IO) {
            val list=_irepo.makeNetworkCall(lat,lon)
            _weatherList.postValue(list)
        }
    }

}