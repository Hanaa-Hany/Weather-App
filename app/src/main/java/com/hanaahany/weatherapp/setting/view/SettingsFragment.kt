package com.hanaahany.weatherapp.setting.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.hanaahany.weatherapp.R
import com.hanaahany.weatherapp.Utils.Constants
import com.hanaahany.weatherapp.Utils.LocationByGPS
import com.hanaahany.weatherapp.databinding.FragmentSettingsBinding
import com.hanaahany.weatherapp.home.viewmodel.HomeViewModel
import com.hanaahany.weatherapp.home.viewmodel.HomeViewModelFactory
import com.hanaahany.weatherapp.services.dp.LocalSource
import com.hanaahany.weatherapp.services.model.Repository
import com.hanaahany.weatherapp.services.network.WeatherClient
import com.hanaahany.weatherapp.services.sharedpref.SettingSharedPrefrences
import kotlinx.coroutines.launch


class SettingsFragment : Fragment() {

    private lateinit var viewModel: HomeViewModel
    private lateinit var binding: FragmentSettingsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val factory = HomeViewModelFactory(
            Repository.getInstance(
                WeatherClient,
                SettingSharedPrefrences.getInstance(requireContext()), LocalSource(requireContext())
            )
        )
        viewModel = ViewModelProvider(this, factory).get(HomeViewModel::class.java)

        handelMarkTemp()
        handelMarkWind()
        handelMarkLang()
        handelMarkLocation()
        handleLanguage()
        handelSpeed()
        handleUnits()
        handleLocation()

    }

    private fun handelMarkLocation() {
        if (viewModel.readStringFromSetting(Constants.LOCATION) == Constants.MAP) {
            binding.radioMap.isChecked = true
        } else {
            binding.radioGps.isChecked = true
        }
    }

    private fun handelMarkLang() {
        if (viewModel.readStringFromSetting(Constants.LANGUAGE) == "ar") {
            binding.radioArabic.isChecked = true
        } else {
            binding.radioEnglish.isChecked = true
        }
    }

    private fun handelMarkWind() {
        if (viewModel.readStringFromSetting(Constants.WIND_SPEED) == "imperial") {
            binding.radioMiles.isChecked = true
        } else {
            binding.radioMeter.isChecked = true
        }

    }

    private fun handelMarkTemp() {
        if (viewModel.readStringFromSetting(Constants.UNIT) == "metric") {
            binding.radioCelisous.isChecked = true
        } else if (viewModel.readStringFromSetting(Constants.UNIT) == "imperial") {
            binding.radioFehraniet.isChecked = true
        } else {
            binding.radioKelvin.isChecked = true
        }
    }

    private fun handleLocation() {
        binding.radioGroupLocation.setOnCheckedChangeListener { radioGroup, radioId ->
            when (radioId) {
                R.id.radio_gps -> {
                    viewModel.writeStringToSetting(Constants.LOCATION, Constants.GPS)
                    LocationByGPS(requireContext()).location.observe(context as LifecycleOwner) {
                        Log.i(Constants.locationTag, it.first.toString())
                        lifecycleScope.launch {
                            val lang = viewModel.readStringFromSetting(Constants.LANGUAGE)
                            val units = viewModel.readStringFromSetting(Constants.UNIT)
                            viewModel.getWeather(it.first, it.second, units, lang)
                        }
                    }

                }
                R.id.radio_map -> {
                    viewModel.writeStringToSetting(Constants.LOCATION, Constants.MAP)

                    val action =
                        SettingsFragmentDirections.actionSettingsFragmentToMapsFragment(Constants.SET_Source)
                    Navigation.findNavController(requireView()).navigate(action)

                }
            }
        }
    }

    private fun handelSpeed() {
        binding.radioGroupSpeed.setOnCheckedChangeListener { _, checkId ->
            if (checkId == R.id.radio_meter) {
                Toast.makeText(requireContext(), "Meter", Toast.LENGTH_SHORT).show()
                viewModel.writeStringToSetting(Constants.WIND_SPEED, "metric")
            } else {
                Toast.makeText(requireContext(), "Miles", Toast.LENGTH_SHORT).show()
                viewModel.writeStringToSetting(Constants.WIND_SPEED, "imperial")


            }
            //restartApplication()
        }
    }

    private fun handleLanguage() {
        Toast.makeText(requireContext(), "Handle", Toast.LENGTH_SHORT).show()
        binding.radioGroupLanguage.setOnCheckedChangeListener { _, checkId ->
            if (checkId == R.id.radio_arabic) {
                Toast.makeText(requireContext(), "Arabic", Toast.LENGTH_SHORT).show()
                Constants.changeLanguage("ar", requireContext())
                viewModel.writeStringToSetting(Constants.LANGUAGE, "ar")
            } else {
                Toast.makeText(requireContext(), "English", Toast.LENGTH_SHORT).show()
                Constants.changeLanguage("en", requireContext())
                viewModel.writeStringToSetting(Constants.LANGUAGE, "en")
            }
            restartApplication()


        }
    }

    private fun handleUnits() {
        binding.radioGroupUnits.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { _, radioId ->

            if (radioId == R.id.radio_fehraniet)
                viewModel.writeStringToSetting(Constants.UNIT, "imperial")
            else if (radioId == R.id.radio_kelvin)
                viewModel.writeStringToSetting(Constants.UNIT, "standard")
            else (radioId == R.id.radio_celisous)
            viewModel.writeStringToSetting(Constants.UNIT, "metric")


            //restartApplication()

        })
    }

    private fun restartApplication() {

        val intent = requireActivity().packageManager.getLaunchIntentForPackage(
            requireActivity().packageName
        )
        intent?.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        requireActivity().finish()
        if (intent != null) {
            startActivity(intent)
        }

    }


}