package com.hanaahany.weatherapp.alert.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.hanaahany.weatherapp.services.model.RepositoryInterface
import com.hanaahany.weatherapp.services.alarm.AlarmSchedular

class AlertViewModelFactory(private val repo: RepositoryInterface, private val alarmScheduler: AlarmSchedular) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        if (modelClass.isAssignableFrom(AlertViewModel::class.java)) {
            return AlertViewModel(repo, alarmScheduler) as T
        }
        return super.create(modelClass, extras)
    }
}