package com.hanaahany.weatherapp.alert.viewmodel


import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hanaahany.weatherapp.Utils.ApiState
import com.hanaahany.weatherapp.Utils.Constants
import com.hanaahany.weatherapp.model.Alarm
import com.hanaahany.weatherapp.model.RepositoryInterface
import com.hanaahany.weatherapp.services.alarm.AlarmSchedular
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class AlertViewModel(private val repo: RepositoryInterface, private val alarmScheduler: AlarmSchedular) :
    ViewModel() {

    private val _alarmsMutableStateFlow: MutableStateFlow<List<Alarm>> = MutableStateFlow(
        emptyList()
    )
    val alarmsStateFlow: StateFlow<List<Alarm>> get() = _alarmsMutableStateFlow

    private val _weatherResponseMutableStateFlow: MutableStateFlow<ApiState> =
        MutableStateFlow(ApiState.Loading)
    val weatherResponseStateFlow: StateFlow<ApiState> get() = _weatherResponseMutableStateFlow


    fun insertAlarm(alarmItem: Alarm) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.insertAlarm(alarmItem)
        }
    }

    fun deleteAlarm(alarmItem: Alarm) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.deleteAlarm(alarmItem)
        }
    }

    fun getAllAlarms() {
        viewModelScope.launch {
            repo.getAllAlarms().collectLatest {
                _alarmsMutableStateFlow.value = it
            }
        }
    }

    fun getCashedData() {
        viewModelScope.launch(Dispatchers.IO) {
            repo.getCachedData()?.let { data ->
                data.collectLatest {
                    if (it != null) {
                        _weatherResponseMutableStateFlow.value = ApiState.Success(it)
                    }
                }
            }
        }
    }

    fun createAlarmScheduler(alarmItem: Alarm, context: Context) {
        alarmScheduler.startAlarm(alarmItem, context)
    }

    fun cancelAlarmScheduler(alarmItem: Alarm, context: Context) {
        alarmScheduler.startAlarm(alarmItem, context)
    }

    fun readStringFromSettingSP(key: String): String {
        return repo.readStringFromSetting(key)
    }

    fun isNotificationEnabled() =
        readStringFromSettingSP(Constants.NOTIFICATION) != Constants.DISABLE

}