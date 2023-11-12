package com.hanaahany.weatherapp.model

import com.hanaahany.weatherapp.data.source.FakeLocalSource
import com.hanaahany.weatherapp.data.source.FakeRemoteSource
import com.hanaahany.weatherapp.data.source.FakeSharedPref
import com.hanaahany.weatherapp.services.model.*
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class RepositoryTest {

    private val firstPlace: Place =
        Place(0, "NewCairo", 31.2125, 30.2121,  "eltgam3", 3.5, "9/11/2023", "",0,0,0,0.0,"",
            emptyList(), emptyList())
    private val secondPlace: Place =
        Place(1, "cairo", 30.2125, 29.2121, "eltgam3", 3.5, "9/11/2023", "",0,0,0,0.0,"",
            emptyList(), emptyList())
    private val thirdPlace: Place =
        Place(2, "Daqhlia", 32.2125, 28.2121, "eltgam3", 3.5, "9/11/2023", "",0,0,0,0.0,"",
            emptyList(), emptyList())

    private val firstAlarm:Alarm= Alarm(0L,"",0.0,0.0,"")
    private val secondAlarm:Alarm=Alarm(0L,"",0.0,0.0,"")
    private val thirdAlarm:Alarm=Alarm(0L,"",0.0,0.0,"")

    private val keyString="KEY"
    private val valueString="value"


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


    private lateinit var fakeLocalSource: FakeLocalSource
    private lateinit var fakeRemoteSource: FakeRemoteSource
    private lateinit var fakeSettingSharedPref: FakeSharedPref
    private lateinit var repo: RepositoryInterface

    @Before
    fun setUp() {
        fakeLocalSource = FakeLocalSource(
            mutableListOf()
        )
        fakeRemoteSource = FakeRemoteSource(weatherResponse)
        fakeSettingSharedPref = FakeSharedPref("", 0.0f)

        repo = Repository.getInstance(
            fakeRemoteSource,
            fakeSettingSharedPref,
            fakeLocalSource

        )
    }
    @Test
    fun deletePlaceFromFavTest_Place_DeletedPlace() = runBlocking {
        //when
        repo.deleteLocationFromDB(firstPlace)

        var result: Boolean? = null
        repo.getFavLocation().collect {
            result = it.contains(firstPlace)
        }

        //then
        assertEquals(false, result)

    }

    @Test
    fun insertPlaceToFavTest_ListOfPlaces_PlacesInsertDone() = runBlocking {
        //when
        repo.insertFavLocation(firstPlace)
        var result: Boolean? = null
        repo.getFavLocation().collect {
            result = it.contains(firstPlace)
        }

        //then
        assertEquals(true, result)
        repo.deleteLocationFromDB(firstPlace)

    }


    @Test
    fun getAllFavPlacesTest_GetAllPlaces() = runBlocking {
        //when
        repo.insertFavLocation(firstPlace)
        repo.insertFavLocation(secondPlace)
        repo.insertFavLocation(thirdPlace)
        val result = repo.getFavLocation()

        //then
        result.collect {
            assertEquals(listOf(firstPlace,secondPlace,thirdPlace), it)
        }
    }

    @Test
    fun getAllAlarmsTest_ListOfAlarm()= runBlocking{
        //When
        repo.insertAlarm(firstAlarm)
        repo.insertAlarm(secondAlarm)
        repo.insertAlarm(thirdAlarm)
        val result=repo.getAllAlarms()

        //Then
        result.collect{
            assertEquals(listOf(firstAlarm,secondAlarm,thirdAlarm),it)

        }




    }

    @Test
    fun deleteAlarmTest_Alarm_Deleted()= runBlocking {
        //when
        repo.deleteAlarm(firstAlarm)
        var result:Boolean? =null
            repo.getAllAlarms().collect{
                result=it.contains(firstAlarm)
            }
        //then
        assertEquals(false,result)
    }

    @Test
    fun insertAlarmTest_AlarmInserted()= runBlocking {
        //when
        repo.insertAlarm(firstAlarm)
        var result:Boolean?=null
        repo.getAllAlarms().collect{
            result=it.contains(firstAlarm)
        }
        //then
        assertEquals(true,result)
    }

    @Test
    fun sharedPrefStringToSettingTest_WriteAndReadInDB(){
        //when
        repo.writeStringToSetting(keyString,valueString)
        val result:String =repo.readStringFromSetting(keyString)
        //then
        assertEquals(valueString,result)
    }




}