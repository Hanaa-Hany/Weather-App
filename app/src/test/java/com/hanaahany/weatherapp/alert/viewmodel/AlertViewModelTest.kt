package com.hanaahany.weatherapp.alert.viewmodel


import app.cash.turbine.test
import com.hanaahany.weatherapp.data.source.FakeReository
import com.hanaahany.weatherapp.model.MainDispatcherRule
import com.hanaahany.weatherapp.services.alarm.AlarmSchedular
import com.hanaahany.weatherapp.services.model.*
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import junit.framework.Assert.*



class AlertViewModelTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    val firstAlarm=Alarm(0L,"",0.0,0.0,"")
    val secondAlarm=Alarm(0L,"",0.0,0.0,"")
    val thirdAlarm=Alarm(0L,"",0.0,0.0,"")
    private val weatherResponse: WeatherResponse = WeatherResponse(
        0,
        0.0,
        0.0,
        "",
        CurrentWeather(0L, 0.0, 0.0, 0, 0, 0, 0.0, emptyList()),
        emptyList(),
        emptyList(),
        emptyList()
    )

    private lateinit var repo: RepositoryInterface
    private lateinit var viewModel: AlertViewModel
    private lateinit var alramSchedular: AlarmSchedular

    @Before
    fun setUp() {

        repo = FakeReository(
            weather = weatherResponse,
            read = "",
            places = mutableListOf(),
            alarm = mutableListOf(),
            readFloat = 0.0f
        )
        alramSchedular = mockk<AlarmSchedular>()
        //val context: Application = ApplicationProvider.getApplicationContext()
        //alramSchedular=AlarmSchedular.getInstance(context)


        viewModel = AlertViewModel(repo,alramSchedular)
    }


    @Test
    fun insertAlarmTest_Alarm_ListOfAlarms()= runBlocking{
        //when
        viewModel.insertAlarm(firstAlarm)
        var result: List<Alarm> = listOf()
        viewModel.alarmsStateFlow.test {
            result = this.awaitItem()
            println("Result after inserting alarm: $result")
        }
        //Then
        assertTrue(result.contains(firstAlarm))
    }

    @Test
    fun getAllAlarmTest_ListOfAlarms()= runBlocking{
        //when
        viewModel.insertAlarm(firstAlarm)
        viewModel.insertAlarm(secondAlarm)
        viewModel.insertAlarm(thirdAlarm)
        viewModel.getAllAlarms()
        var result: List<Alarm> = listOf()
        viewModel.alarmsStateFlow.test {
            result = this.awaitItem()
            println("Result after get All alarm: $result")
        }
        //Then
        assertEquals(listOf(firstAlarm,secondAlarm,thirdAlarm),result)
    }

    @Test
    fun deleteAlarmTest_Alarm_UpdateListOfAlarms()= runBlocking{
        //when
        viewModel.insertAlarm(firstAlarm)
        viewModel.insertAlarm(secondAlarm)
        viewModel.insertAlarm(thirdAlarm)
        viewModel.deleteAlarm(secondAlarm)
        var result: List<Alarm> = listOf()
        viewModel.alarmsStateFlow.test {
            result = this.awaitItem()
            println("Result after delete alarm: $result")
        }
        //Then
        assertEquals(listOf<Alarm>(firstAlarm,thirdAlarm),result)
    }


}