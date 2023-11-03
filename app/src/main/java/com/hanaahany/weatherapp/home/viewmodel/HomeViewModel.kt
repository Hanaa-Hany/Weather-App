package com.hanaahany.weatherapp.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hanaahany.weatherapp.model.RepositoryInterface
import com.hanaahany.weatherapp.model.WeatherResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class HomeViewModel(private val _irepo: RepositoryInterface) : ViewModel() {

    private val _respone = MutableLiveData<Response<WeatherResponse>>()
    var respone: LiveData<Response<WeatherResponse>> = _respone


    fun getWeather(lat: Double, lon: Double) {
        viewModelScope.launch(Dispatchers.IO) {
            _irepo.makeNetworkCall(lat, lon)
            _respone.postValue(
                _irepo.makeNetworkCall(lat, lon)
            )

        }

    }

}