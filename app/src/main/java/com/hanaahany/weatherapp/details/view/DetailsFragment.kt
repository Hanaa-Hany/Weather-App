package com.hanaahany.weatherapp.details.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.hanaahany.weatherapp.R
import com.hanaahany.weatherapp.Utils.ApiState
import com.hanaahany.weatherapp.Utils.Constants
import com.hanaahany.weatherapp.databinding.FragmentDetailsBinding
import com.hanaahany.weatherapp.databinding.FragmentHomeBinding
import com.hanaahany.weatherapp.dp.LocalSource
import com.hanaahany.weatherapp.home.view.DayAdapter
import com.hanaahany.weatherapp.home.view.HourAdapter
import com.hanaahany.weatherapp.home.viewmodel.HomeViewModel
import com.hanaahany.weatherapp.home.viewmodel.HomeViewModelFactory
import com.hanaahany.weatherapp.model.Place
import com.hanaahany.weatherapp.model.Repository
import com.hanaahany.weatherapp.model.WeatherResponse
import com.hanaahany.weatherapp.network.WeatherClient
import com.hanaahany.weatherapp.network.sharedpref.SettingSharedPrefrences
import kotlinx.coroutines.launch


class DetailsFragment : Fragment() {
    private lateinit var viewModel: HomeViewModel
    lateinit var binding: FragmentDetailsBinding
    private lateinit var hourAdapter: HourAdapter
    private lateinit var dayAdapter: DayAdapter
    private var latitude:Double=0.0
    private var langitude:Double=0.0
    private lateinit var place: Place

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentDetailsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        val factory = HomeViewModelFactory(
            Repository.getInstance(
                WeatherClient,
            SettingSharedPrefrences.getInstance(requireContext()), LocalSource(requireContext())
        ))

        viewModel = ViewModelProvider(this, factory).get(HomeViewModel::class.java)

        place =DetailsFragmentArgs.fromBundle(requireArguments()).detailsFav
        Log.i(Constants.locationTag,"Details"+place.city)
        setData()
        hourAdapter = HourAdapter(requireContext(), place.hourly)
        binding.recyclerHourlyDay.adapter = hourAdapter
        dayAdapter = DayAdapter(requireContext(), place.daily)
        binding.recyclerWeakTemp.adapter = dayAdapter

    }

    private fun initViews() {
        val layoutManager=
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL,false)
        binding.recyclerHourlyDay.layoutManager=layoutManager
        val layoutManagerDaily=
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)
        binding.recyclerWeakTemp.layoutManager=layoutManagerDaily

    }

    @SuppressLint("SetTextI18n")
    private fun setData() {
        binding.tvTempDetailsFragment.text =
            Constants.writeDegree(requireContext(), place.temp.toInt().toString())
        binding.tvCloudsValueDetailsFragment.text=place.cloud.toString()
        binding.tvPressureValueDetailsFragment.text=place.pressure.toString()
        binding.tvWindValueHomeFragment.text= Constants.windSpeed(requireContext(), place.windSpeed.toString())
        binding.tvCityDetailsFragment.text =place.cityName
           // Constants.setLocationNameByGeoCoder(requireContext(), place.latitude, place.longitude)
        binding.tvHumidityValueDetailsFragment.text=place.humidity.toString()

        binding.tvWeatherDescriptionDetailsFragment.text=place.description
        //Constants.setIcon("https://openweathermap.org/img/wn/${place.icon}@4x.png",binding.iconDetailsFragment)
        Constants.setIcon(place.icon,binding.iconDetailsFragment)

        if (SettingSharedPrefrences.getInstance(requireContext()).readStringSettings(Constants.LANGUAGE)=="en"){
            //binding.tvWeatherTimeDetailsFragment.text= Constants.getTime(it.current.dt,"en")
            binding.tvWeatherDateDetailsFragment.text= place.date
        }else{
            //binding.tvWeatherTimeDetailsFragment.text= Constants.getTime(it.current.dt,"ar")
            binding.tvWeatherDateDetailsFragment.text= place.date

        }
//
//
//    }

    }
}