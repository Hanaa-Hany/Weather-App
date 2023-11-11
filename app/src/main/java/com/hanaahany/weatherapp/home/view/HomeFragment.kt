package com.hanaahany.weatherapp.home.view


import android.annotation.SuppressLint
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
import com.hanaahany.weatherapp.Utils.Permission
import com.hanaahany.weatherapp.databinding.FragmentHomeBinding
import com.hanaahany.weatherapp.dp.LocalSource
import com.hanaahany.weatherapp.home.viewmodel.HomeViewModel
import com.hanaahany.weatherapp.home.viewmodel.HomeViewModelFactory
import com.hanaahany.weatherapp.maps.MapsFragmentArgs
import com.hanaahany.weatherapp.model.Repository
import com.hanaahany.weatherapp.model.WeatherResponse
import com.hanaahany.weatherapp.services.network.WeatherClient
import com.hanaahany.weatherapp.services.sharedpref.SettingSharedPrefrences
import kotlinx.coroutines.launch


class HomeFragment : Fragment() {
    private lateinit var viewModel:HomeViewModel
    lateinit var binding: FragmentHomeBinding
    private lateinit var hourAdapter: HourAdapter
    private lateinit var dayAdapter: DayAdapter
    private var latitude:Double=0.0
    private var langitude:Double=0.0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        flag=true
        binding=FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        val location = LocationByGPS(requireContext())
        location.getLastLocation()


        val factory = HomeViewModelFactory(Repository.getInstance(
            WeatherClient,
            SettingSharedPrefrences.getInstance(requireContext()), LocalSource(requireContext())
        ))

        viewModel = ViewModelProvider(this, factory).get(HomeViewModel::class.java)

        location.location.observe(context as LifecycleOwner) {
            Log.i(Constants.locationTag, it.first.toString())
            lifecycleScope.launch {
                val lang=viewModel.readStringFromSetting(Constants.LANGUAGE)
                val units=viewModel.readStringFromSetting(Constants.UNIT)
                if (Permission.checkConnection(requireContext())){
                    val per=Permission.checkPermission(requireContext())
                    Log.i(Constants.locationTag,per.toString())
                    viewModel.getWeather(it.first, it.second,units,lang)

                }else{
                    viewModel.getCachedWeather()
                    Log.i(Constants.locationTag,"else"+it.first)
                }

                latitude=it.first
                langitude=it.second
            }


        }
        lifecycleScope.launch {
            viewModel.respone.collect { result ->
                when (result) {
                    is ApiState.Success -> {
                        viewModel.insertCachedWeather(result.date)
                        result.date.lat=latitude
                        result.date.long=langitude

                        binding.loadingLottiHomeFragment.visibility=View.GONE
                        handleView(View.VISIBLE)

                        setData(result.date)
                        Log.i(Constants.locationTag,result.date.lat.toString()+"result")
                        hourAdapter = HourAdapter(requireContext(), result.date.hourly)
                        binding.recyclerHourlyDay.adapter = hourAdapter
                        dayAdapter = DayAdapter(requireContext(), result.date.daily)
                        binding.recyclerWeakTemp.adapter = dayAdapter
                    }
                    is ApiState.Fail -> {
                        Toast.makeText(requireContext(), "Server Error", Toast.LENGTH_SHORT).show()
                    }
                    else -> {
                        Log.i(Constants.locationTag,"Loading")
                        binding.loadingLottiHomeFragment.visibility=View.VISIBLE
                        handleView(View.INVISIBLE)

                    }
                }
            }
        }
        getDataFromMap()

    }

    private fun getDataFromMap() {
        if(flag) {

        }else{
            val lat = HomeFragmentArgs.fromBundle(requireArguments()).latitude
            HomeFragmentArgs.fromBundle(requireArguments()).langitude
            Log.i(Constants.locationTag, "$lat HomeFragment")
        }
    }

    private fun handleView(appear: Int) {
        binding.card3.visibility=appear
        binding.card4.visibility=appear
        binding.card5.visibility=appear
        binding.card6.visibility=appear
    }

    private fun initViews() {
        val layoutManager=LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
        binding.recyclerHourlyDay.layoutManager=layoutManager
        val layoutManagerDaily=LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
        binding.recyclerWeakTemp.layoutManager=layoutManagerDaily

    }

    @SuppressLint("SetTextI18n")
    private fun setData(it:WeatherResponse) {
        binding.tvTempHomeFragment.text=Constants.writeDegree(requireContext(),it.current.temp.toInt().toString())
        binding.tvCloudsValueHomeFragment.text=it.current.clouds.toString()
        binding.tvPressureValueHomeFragment.text=it.current.pressure.toString()
        binding.tvWindValueHomeFragment.text=Constants.windSpeed(requireContext(), it.current.wind_speed.toString())
        binding.tvCityHomeFragment.text=Constants.setLocationNameByGeoCoder(requireContext(),it.lat,it.long)
        //Log.i(Constants.locationTag,it.lat.toString()+"setData")
        binding.tvHumidityValueHomeFragment.text=it.current.humidity.toString()
        binding.tvWeatherDescriptionHomeFragment.text=it.current.weather.get(0).description
        Constants.setIcon(it.current.weather.get(0).icon,binding.iconHomeFragment)
        if (SettingSharedPrefrences.getInstance(requireContext()).readStringSettings(Constants.LANGUAGE)=="ar"){
            binding.tvWeatherTimeHomeFragment.text=Constants.getTime(it.current.dt,"ar")
            binding.tvWeatherDateHomeFragment.text=Constants.getDate(it.current.dt,"ar")
        }else{
            binding.tvWeatherTimeHomeFragment.text=Constants.getTime(it.current.dt,"en")
            binding.tvWeatherDateHomeFragment.text=Constants.getDate(it.current.dt,"en")

        }


    }

    companion object{
        var flag=true
    }

}