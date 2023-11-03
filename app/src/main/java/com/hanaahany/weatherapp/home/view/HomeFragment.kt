package com.hanaahany.weatherapp.home.view


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.hanaahany.weatherapp.Utils.Constants
import com.hanaahany.weatherapp.Utils.LocationByGPS
import com.hanaahany.weatherapp.databinding.FragmentHomeBinding
import com.hanaahany.weatherapp.home.viewmodel.HomeViewModel
import com.hanaahany.weatherapp.home.viewmodel.HomeViewModelFactory
import com.hanaahany.weatherapp.model.Repository
import com.hanaahany.weatherapp.model.WeatherResponse
import com.hanaahany.weatherapp.network.WeatherClient
import kotlinx.coroutines.Dispatchers
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
        val location=LocationByGPS(requireContext())
        location.getLastLocation()
        val productFac=HomeViewModelFactory(Repository.getInstance(WeatherClient))

        viewModel= ViewModelProvider(this,productFac).get(HomeViewModel::class.java)


        location.location.observe(context as LifecycleOwner){
            Log.i(Constants.locationTag,it.first.toString())
            lifecycleScope.launch (Dispatchers.Main){
                viewModel.getWeather(it.first,it.second)
                Log.i(Constants.locationTag,"viewModel.getWeather(it.first,it.second)")
            }
           // binding.tvCityHomeFragment.text=

        }
        viewModel.respone.observe(context as LifecycleOwner){
            setData(it)
            hourAdapter=HourAdapter(requireContext(),it.body()!!.hourly)
            binding.recyclerHourlyDay.adapter=hourAdapter
            dayAdapter=DayAdapter(requireContext(),it.body()!!.daily)
            binding.recyclerWeakTemp.adapter=dayAdapter

        }




    }

    private fun initViews() {
        val layoutManager=LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
        binding.recyclerHourlyDay.layoutManager=layoutManager
        val layoutManagerDaily=LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
        binding.recyclerWeakTemp.layoutManager=layoutManagerDaily

    }

    private fun setData(it: Response<WeatherResponse>) {
        binding.tvTempHomeFragment.text=it.body()!!.current.temp.toInt().toString()
        binding.tvCloudsValueHomeFragment.text=it.body()!!.current.clouds.toString()
        binding.tvPressureValueHomeFragment.text=it.body()!!.current.pressure.toString()
        binding.tvWindValueHomeFragment.text=it.body()!!.current.wind_speed.toString()
        binding.tvHumidityValueHomeFragment.text=it.body()!!.current.humidity.toString()
        binding.tvWeatherDescriptionHomeFragment.text=it.body()!!.current.weather.get(0).description
        Log.i(Constants.locationTag,"https://openweathermap.org/img/wn/${it.body()!!.current.weather.get(0).icon}@2x.png")
        Glide.with(requireContext()).load("https://openweathermap.org/img/wn/${it.body()!!.current.weather.get(0).icon}@4x.png")
            .into( binding.iconHomeFragment)
        binding.tvWeatherTimeHomeFragment.text=Constants.getTime(it.body()!!.current.dt)
        binding.tvWeatherDateHomeFragment.text=Constants.getDate(it.body()!!.current.dt)

    }


}