package com.hanaahany.weatherapp.home.viewmodel

import com.hanaahany.weatherapp.data.source.FakeReository
import com.hanaahany.weatherapp.model.MainDispatcherRule
import com.hanaahany.weatherapp.services.model.CurrentWeather
import com.hanaahany.weatherapp.services.model.Place
import com.hanaahany.weatherapp.services.model.RepositoryInterface
import com.hanaahany.weatherapp.services.model.WeatherResponse
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class HomeViewModelTest(){
    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val firstPlace: Place =
        Place(0, "NewCairo", 31.2125, 30.2121, "eltgam3", 3.5, "9/11/2023", "",0,0,0,0.0,"",
            emptyList(), emptyList())
    private val secondPlace: Place =
        Place(1, "cairo", 30.2125, 29.2121, "eltgam3", 3.5, "9/11/2023", "",0,0,0,0.0,"",
            emptyList(), emptyList()
        )
    private val thirdPlace: Place =
        Place(2, "Daqhlia", 32.2125, 28.2121, "eltgam3", 3.5, "9/11/2023", "",0,0,0,0.0,"",
            emptyList(), emptyList())
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
    private lateinit var viewModel: HomeViewModel

    @Before
    fun setUp() {

        repo = FakeReository(weather = weatherResponse, read = "", places = mutableListOf(), alarm = mutableListOf(), readFloat = 0.0f)

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