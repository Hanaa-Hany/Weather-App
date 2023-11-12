package com.hanaahany.weatherapp.home.viewmodel

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.hanaahany.weatherapp.data.source.FakeLocalSource
import com.hanaahany.weatherapp.data.source.FakeRemoteSource
import com.hanaahany.weatherapp.data.source.FakeReository
import com.hanaahany.weatherapp.data.source.FakeSharedPref
import com.hanaahany.weatherapp.model.*
import com.hanaahany.weatherapp.services.model.CurrentWeather
import com.hanaahany.weatherapp.services.model.Place
import com.hanaahany.weatherapp.services.model.RepositoryInterface
import com.hanaahany.weatherapp.services.model.WeatherResponse
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.runBlocking
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class HomeViewModelTest(){
    private val firstPlace: Place =
        Place(0, "NewCairo", 31.2125, 30.2121, "eltgam3", 3.5, "9/11/2023", "")
    private val secondPlace: Place =
        Place(1, "cairo", 30.2125, 29.2121, "eltgam3", 3.5, "9/11/2023", "")
    private val thirdPlace: Place =
        Place(2, "Daqhlia", 32.2125, 28.2121, "eltgam3", 3.5, "9/11/2023", "")
    private val weatherResponse: WeatherResponse = WeatherResponse(
        0,
        0.0,
        0.0,
        "",
        CurrentWeather(0L, 0.0, 0.0, 0, 0, 0, 0.0, emptyList()),
        emptyList(),
        emptyList()
    )



    private lateinit var repo: RepositoryInterface
    private lateinit var viewModel: HomeViewModel

    @Before
    fun setUp() {

        repo = FakeReository(weather = weatherResponse, stringReadValue = "")

        viewModel= HomeViewModel(repo)
    }


    @Test
    fun insertFavLocation_Place_IncreaseListOfPlaces()= runBlocking{
        //When
        viewModel.insertFavLocation(firstPlace)
        viewModel.getFavLocation()
        var result: List<Place> = listOf()
        viewModel.favLocation.collect{
            result = it
        }
        assertTrue(result.contains(firstPlace))

    }
    //then

}