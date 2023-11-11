package com.hanaahany.weatherapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.hanaahany.weatherapp.databinding.ActivityMainBinding
import com.hanaahany.weatherapp.databinding.HourlyTempLayoutBinding
import com.hanaahany.weatherapp.favourite.view.FavouriteFragment
import com.hanaahany.weatherapp.home.view.HomeFragment
import com.hanaahany.weatherapp.setting.view.SettingsFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpNavigation()
        toggleFragment()
        binding.tvHome.setTextColor(getColor(R.color.text_option))





    }

    private fun setUpNavigation() {
        navController=Navigation.findNavController(this,R.id.nav_host_fragment)
        //NavigationUI.setupActionBarWithNavController(this,navController)
    }

    private fun toggleFragment() {
        binding.tvHome.setOnClickListener{
            navController.navigate(R.id.homeFragment)
            binding.tvHome.setTextColor(getColor(R.color.text_option))
            binding.tvFav.setTextColor(getColor(R.color.text_color))
            binding.tvSettings.setTextColor(getColor(R.color.text_color))
            binding.tvAlert.setTextColor(getColor(R.color.text_color))
        }
        binding.tvFav.setOnClickListener{
            navController.navigate(R.id.favouriteFragment)
            binding.tvHome.setTextColor(getColor(R.color.text_color))
            binding.tvFav.setTextColor(getColor(R.color.text_option))
            binding.tvSettings.setTextColor(getColor(R.color.text_color))
            binding.tvAlert.setTextColor(getColor(R.color.text_color))

        }
        binding.tvSettings.setOnClickListener{
            navController.navigate(R.id.settingsFragment)
            binding.tvHome.setTextColor(getColor(R.color.text_color))
            binding.tvFav.setTextColor(getColor(R.color.text_color))
            binding.tvSettings.setTextColor(getColor(R.color.text_option))
            binding.tvAlert.setTextColor(getColor(R.color.text_color))

        }
        binding.tvAlert.setOnClickListener{
            navController.navigate(R.id.alertFragment)
            binding.tvHome.setTextColor(getColor(R.color.text_color))
            binding.tvFav.setTextColor(getColor(R.color.text_color))
            binding.tvSettings.setTextColor(getColor(R.color.text_color))
            binding.tvAlert.setTextColor(getColor(R.color.text_option))

        }
    }


}