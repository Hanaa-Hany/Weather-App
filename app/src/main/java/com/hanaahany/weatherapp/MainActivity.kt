package com.hanaahany.weatherapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.hanaahany.weatherapp.databinding.ActivityMainBinding
import com.hanaahany.weatherapp.databinding.HourlyTempLayoutBinding
import com.hanaahany.weatherapp.favourite.view.FavouriteFragment
import com.hanaahany.weatherapp.home.view.HomeFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportFragmentManager.beginTransaction().replace(R.id.homeFragment,HomeFragment())
            .commit()
        toggleFragment()



    }

    private fun toggleFragment() {
        binding.tvHome.setOnClickListener{
            changeFragment(HomeFragment())
        }
        binding.tvFav.setOnClickListener{
            changeFragment(FavouriteFragment())
        }
        binding.tvSettings.setOnClickListener{
        }
        binding.tvAlert.setOnClickListener{
        }
    }

    private fun changeFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.homeFragment,fragment)
            .commit()
    }
}