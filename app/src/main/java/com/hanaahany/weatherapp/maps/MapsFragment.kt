package com.hanaahany.weatherapp.maps

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.hanaahany.weatherapp.R
import com.hanaahany.weatherapp.Utils.Constants
import com.hanaahany.weatherapp.Utils.LocationByGPS
import com.hanaahany.weatherapp.databinding.FragmentMapsBinding
import com.hanaahany.weatherapp.dp.LocalSource
import com.hanaahany.weatherapp.favourite.view.FavouriteFragment
import com.hanaahany.weatherapp.home.view.HomeFragment
import com.hanaahany.weatherapp.home.viewmodel.HomeViewModel
import com.hanaahany.weatherapp.home.viewmodel.HomeViewModelFactory
import com.hanaahany.weatherapp.model.Place
import com.hanaahany.weatherapp.model.Repository
import com.hanaahany.weatherapp.model.WeatherResponse
import com.hanaahany.weatherapp.network.WeatherClient
import com.hanaahany.weatherapp.network.sharedpref.SettingSharedPrefrences
import kotlinx.coroutines.launch

class MapsFragment : Fragment() {

    private lateinit var mMap:GoogleMap
    private lateinit var binding: FragmentMapsBinding
    private var latitude=0.0
    private var longitude=0.0
    private lateinit var viewModel:HomeViewModel
    private  var  callback:OnMapReadyCallback = OnMapReadyCallback { googleMap ->
        mMap = googleMap

            val sydney = LatLng(LocationByGPS.lat,LocationByGPS.lang)
            mMap.addMarker(MarkerOptions().position(sydney).title(""))
            mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney,12f))


        mMap.setOnMapClickListener {
            mMap.clear()
            mMap.addMarker(MarkerOptions().position(it).title(""))
            mMap.moveCamera(CameraUpdateFactory.newLatLng(it))
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(it,12f))
            latitude=it.latitude
            longitude=it.longitude

            Log.i(Constants.locationTag,"hh"+latitude.toString())

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentMapsBinding.inflate(inflater,container,false)
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
        ))

        viewModel = ViewModelProvider(this, factory).get(HomeViewModel::class.java)

        binding.btnSave.setOnClickListener {
            viewModel.writeFloatToSetting(Constants.LAT,latitude.toFloat())
            viewModel.writeFloatToSetting(Constants.LAN,longitude.toFloat())
            requireFragmentManager().beginTransaction().replace(R.id.homeFragment,FavouriteFragment()).commit()
            viewModel.getWeather(latitude,longitude)
            viewModel.insertFavLocation(Place(cityName = "", latitude = latitude, longitude = longitude))

        }

        }






}