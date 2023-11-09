package com.hanaahany.weatherapp.maps

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.hanaahany.weatherapp.R
import com.hanaahany.weatherapp.Utils.ApiState
import com.hanaahany.weatherapp.Utils.Constants
import com.hanaahany.weatherapp.Utils.LocationByGPS
import com.hanaahany.weatherapp.databinding.FragmentMapsBinding
import com.hanaahany.weatherapp.dp.LocalSource
import com.hanaahany.weatherapp.favourite.view.FavouriteFragment
import com.hanaahany.weatherapp.home.viewmodel.HomeViewModel
import com.hanaahany.weatherapp.home.viewmodel.HomeViewModelFactory
import com.hanaahany.weatherapp.model.DailyWeather
import com.hanaahany.weatherapp.model.HourlyWeather
import com.hanaahany.weatherapp.model.Place
import com.hanaahany.weatherapp.model.Repository
import com.hanaahany.weatherapp.network.WeatherClient
import com.hanaahany.weatherapp.network.sharedpref.SettingSharedPrefrences
import kotlinx.coroutines.launch

class MapsFragment : Fragment() {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: FragmentMapsBinding
    private var latitude = 0.0
    private var longitude = 0.0
    private lateinit var viewModel: HomeViewModel
    private var temp: Double = 0.0
    private var date: String = ""
    private var pressure: Int = 0
    private var humidity: Int = 0
    private var clouds: Int = 0
    private var windSpeed: Double = 0.0
    private var description: String = ""
    private var icon: String = ""
    var hourly: List<HourlyWeather> = emptyList()
    var daily: List<DailyWeather> = emptyList()
    private var callback: OnMapReadyCallback = OnMapReadyCallback { googleMap ->
        mMap = googleMap

        val sydney = LatLng(LocationByGPS.lat, LocationByGPS.lang)
        mMap.addMarker(MarkerOptions().position(sydney).title(""))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 12f))


        mMap.setOnMapClickListener {
            mMap.clear()
            mMap.addMarker(MarkerOptions().position(it).title(""))
            mMap.moveCamera(CameraUpdateFactory.newLatLng(it))
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(it, 12f))
            latitude = it.latitude
            longitude = it.longitude
            viewModel.getWeather(it.latitude, it.longitude)

            Log.i(Constants.locationTag, "hh" + latitude.toString())

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMapsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
        val factory = HomeViewModelFactory(
            Repository.getInstance(
                WeatherClient,
                SettingSharedPrefrences.getInstance(requireContext()), LocalSource(requireContext())
            )
        )

        viewModel = ViewModelProvider(this, factory).get(HomeViewModel::class.java)

        binding.btnSave.setOnClickListener {
            viewModel.writeFloatToSetting(Constants.LAT, latitude.toFloat())
            viewModel.writeFloatToSetting(Constants.LAN, longitude.toFloat())
            Navigation.findNavController(requireView()).navigate(R.id.action_mapsFragment_to_favouriteFragment)


            lifecycleScope.launch {
                viewModel.respone.collect {
                    when (it) {
                        is ApiState.Success -> {

                            temp = it.date.current.temp
                            date = Constants.getDate(it.date.current.dt, "en")
                            icon=it.date.current.weather.get(0).icon
                            pressure=it.date.current.pressure
                            humidity =it.date.current.humidity
                            clouds=it.date.current.clouds
                            windSpeed =it.date.current.wind_speed
                            description=it.date.current.weather.get(0).description
                            hourly=it.date.hourly
                            daily=it.date.daily
                            Log.i(Constants.locationTag,temp.toString())
                            Log.i(Constants.locationTag,date)
                            Log.i(Constants.locationTag,icon)
                        }
                        is ApiState.Fail -> Toast.makeText(
                            requireContext(),
                            "Server Error",
                            Toast.LENGTH_SHORT
                        ).show()
                        is ApiState.Loading -> Log.i(Constants.locationTag, "Loading")

                    }
                }
            }
            val cityName =
                Constants.setLocationNameByGeoCoder(requireContext(), latitude, longitude)
            val city = Constants.setCityNameByGeoCoder(requireContext(), latitude, longitude)
            viewModel.insertFavLocation(
                Place(

                    city = city,
                    cityName = cityName,
                    latitude = latitude,
                    longitude = longitude,
                    temp = temp,
                    date = date,
                    icon = icon,
                    pressure = pressure,
                    humidity =humidity ,
                    cloud = clouds,
                    windSpeed =windSpeed ,
                    description = description,
                    hourly = hourly,
                    daily = daily
                )
            )

        }
    }
}