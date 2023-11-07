package com.hanaahany.weatherapp.setting.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.hanaahany.weatherapp.R
import com.hanaahany.weatherapp.Utils.Constants
import com.hanaahany.weatherapp.databinding.FragmentSettingsBinding
import com.hanaahany.weatherapp.home.viewmodel.HomeViewModel
import com.hanaahany.weatherapp.home.viewmodel.HomeViewModelFactory
import com.hanaahany.weatherapp.model.Repository
import com.hanaahany.weatherapp.network.WeatherClient
import com.hanaahany.weatherapp.network.sharedpref.SettingSharedPrefrences


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
                SettingSharedPrefrences.getInstance(requireContext())
            )
        )
        viewModel = ViewModelProvider(this, factory).get(HomeViewModel::class.java)
        handleLanguage()
        handelSpeed()
        handleUnits()

    }

    private fun handelSpeed() {
        binding.radioGroupSpeed.setOnCheckedChangeListener { _, checkId ->
            if (checkId == R.id.radio_meter) {
                Toast.makeText(requireContext(), "Meter", Toast.LENGTH_SHORT).show()

            } else {
                Toast.makeText(requireContext(), "Miles", Toast.LENGTH_SHORT).show()


            }
            restartApplication()
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
            when (radioId) {
                R.id.radio_kelvin ->
                    viewModel.writeStringToSetting(Constants.UNIT, "standard")
                R.id.radio_celisous ->
                    viewModel.writeStringToSetting(Constants.UNIT, "metric")

                R.id.radio_fehraniet ->
                    viewModel.writeStringToSetting(Constants.UNIT, "imperial")

            }
            restartApplication()

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