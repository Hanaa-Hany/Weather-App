package com.hanaahany.weatherapp.home.view


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.hanaahany.weatherapp.Utils.ApiState
import com.hanaahany.weatherapp.Utils.Constants
import com.hanaahany.weatherapp.Utils.LocationByGPS
import com.hanaahany.weatherapp.databinding.FragmentHomeBinding
import com.hanaahany.weatherapp.home.viewmodel.HomeViewModel
import com.hanaahany.weatherapp.home.viewmodel.HomeViewModelFactory
import com.hanaahany.weatherapp.model.Repository
import com.hanaahany.weatherapp.model.WeatherResponse
import com.hanaahany.weatherapp.network.WeatherClient
import com.hanaahany.weatherapp.network.sharedpref.SettingSharedPrefrences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import retrofit2.Response


class HomeFragment : Fragment() {
    private lateinit var viewModel:HomeViewModel
    lateinit var binding: FragmentHomeBinding
    private lateinit var hourAdapter: HourAdapter
    private lateinit var dayAdapter: DayAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        val location = LocationByGPS(requireContext())
        location.getLastLocation()


        val factory = HomeViewModelFactory(Repository.getInstance(WeatherClient,
            SettingSharedPrefrences.getInstance(requireContext())
        ))

        viewModel = ViewModelProvider(this, factory).get(HomeViewModel::class.java)

        location.location.observe(context as LifecycleOwner) {
            Log.i(Constants.locationTag, it.first.toString())
            lifecycleScope.launch {
                var lang=viewModel.readLanguageFromSetting("lang")
                viewModel.getWeather(it.first, it.second,"metric",lang)
                Log.i(Constants.locationTag, "viewModel.getWeather(it.first,it.second)")
            }
        }
        lifecycleScope.launch {
            viewModel.respone.collect { result ->
                when (result) {
                    is ApiState.Success -> {
                        Log.i(Constants.locationTag,"Success")
                        Log.i(Constants.locationTag, "${result.date.hourly.get(0).clouds}")
                        setData(result.date)
                        Log.i(Constants.locationTag,binding.tvWeatherDescriptionHomeFragment.text.toString())

                        hourAdapter = HourAdapter(requireContext(), result.date.hourly)
                        binding.recyclerHourlyDay.adapter = hourAdapter
                        dayAdapter = DayAdapter(requireContext(), result.date.daily)
                        binding.recyclerWeakTemp.adapter = dayAdapter
                    }
                    is ApiState.Fail -> {
                        Log.i(Constants.locationTag,"Fail")

                        Toast.makeText(requireContext(), "Server Error", Toast.LENGTH_SHORT).show()
                    }
                    else -> {
                        Log.i(Constants.locationTag,"Loading")

                    }
                }
            }
        }
    }

    private fun initViews() {
        val layoutManager=LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
        binding.recyclerHourlyDay.layoutManager=layoutManager
        val layoutManagerDaily=LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
        binding.recyclerWeakTemp.layoutManager=layoutManagerDaily

    }

    private fun setData(it:WeatherResponse) {
        binding.tvTempHomeFragment.text=it.current.temp.toInt().toString()
        binding.tvCloudsValueHomeFragment.text=it.current.clouds.toString()
        binding.tvPressureValueHomeFragment.text=it.current.pressure.toString()
        binding.tvWindValueHomeFragment.text=it.current.wind_speed.toString()
        binding.tvHumidityValueHomeFragment.text=it.current.humidity.toString()
        binding.tvWeatherDescriptionHomeFragment.text=it.current.weather.get(0).description
        Log.i(Constants.locationTag,"https://openweathermap.org/img/wn/${it.current.weather.get(0).icon}@2x.png")
        Glide.with(requireContext()).load("https://openweathermap.org/img/wn/${it.current.weather.get(0).icon}@4x.png")
            .into( binding.iconHomeFragment)
        if (SettingSharedPrefrences.getInstance(requireContext()).readLanguage("lang")=="en"){
            binding.tvWeatherTimeHomeFragment.text=Constants.getTime(it.current.dt,"en")
            binding.tvWeatherDateHomeFragment.text=Constants.getDate(it.current.dt,"en")
        }else{
            binding.tvWeatherTimeHomeFragment.text=Constants.getTime(it.current.dt,"ar")
            binding.tvWeatherDateHomeFragment.text=Constants.getDate(it.current.dt,"ar")

        }


    }


}