package com.hanaahany.weatherapp.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hanaahany.weatherapp.services.model.RepositoryInterface

class HomeViewModelFactory(private val _irepo: RepositoryInterface): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(_irepo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}