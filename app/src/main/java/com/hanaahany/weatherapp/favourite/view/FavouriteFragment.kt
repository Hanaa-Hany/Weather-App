package com.hanaahany.weatherapp.favourite.view

import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.hanaahany.weatherapp.R
import com.hanaahany.weatherapp.Utils.Constants
import com.hanaahany.weatherapp.databinding.FavLayoutBinding
import com.hanaahany.weatherapp.databinding.FragmentFavouriteBinding
import com.hanaahany.weatherapp.dp.LocalSource
import com.hanaahany.weatherapp.home.viewmodel.HomeViewModel
import com.hanaahany.weatherapp.home.viewmodel.HomeViewModelFactory
import com.hanaahany.weatherapp.maps.MapsFragment
import com.hanaahany.weatherapp.model.Place
import com.hanaahany.weatherapp.model.Repository
import com.hanaahany.weatherapp.network.WeatherClient
import com.hanaahany.weatherapp.network.sharedpref.SettingSharedPrefrences
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class FavouriteFragment : Fragment() {

    private lateinit var binding: FragmentFavouriteBinding
    private lateinit var viewModel:HomeViewModel
    private lateinit var adapter: FavouriteAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentFavouriteBinding.inflate(inflater,container,false)
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
        viewModel.getFavLocation()

        lifecycleScope.launch {
            viewModel.favLocation.collect{
                Log.i(Constants.FAV_TAG,it.size.toString())

                adapter= FavouriteAdapter(requireContext()){
                    deleteItem(it)
                }
                adapter.submitList(it)
                binding.recyclerFav.adapter=adapter
            }
        }
        binding.fabFav.setOnClickListener{
            requireFragmentManager().beginTransaction().replace(R.id.homeFragment,MapsFragment()).commit()


            }

    }

    private fun deleteItem(it: Place) {
        viewModel.deleteFavLocation(it)
    }

    private fun initViews() {

    }


}