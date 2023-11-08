package com.hanaahany.weatherapp.favourite.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.hanaahany.weatherapp.R
import com.hanaahany.weatherapp.Utils.Constants
import com.hanaahany.weatherapp.databinding.FragmentFavouriteBinding
import com.hanaahany.weatherapp.dp.LocalSource
import com.hanaahany.weatherapp.home.viewmodel.HomeViewModel
import com.hanaahany.weatherapp.home.viewmodel.HomeViewModelFactory
import com.hanaahany.weatherapp.maps.MapsFragment
import com.hanaahany.weatherapp.model.Repository
import com.hanaahany.weatherapp.network.WeatherClient
import com.hanaahany.weatherapp.network.sharedpref.SettingSharedPrefrences
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
                    deleteItem()
                }
                adapter.submitList(it)
                binding.recyclerFav.adapter=adapter
            }
        }
        binding.fabFav.setOnClickListener{
            requireFragmentManager().beginTransaction().replace(R.id.homeFragment,MapsFragment()).commit()
            }
    }



    private fun deleteItem() {

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback( 0,ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {

                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {


                val position = viewHolder.adapterPosition
                var place = adapter.currentList[position]
                viewModel.deleteFavLocation(place)
                Snackbar.make(binding.recyclerFav, "Deleted " + place.city, Snackbar.LENGTH_LONG)
                    .setAction(
                        "Undo",
                        View.OnClickListener {
                           viewModel.insertFavLocation(place)
                        }).show()
            }

        }).attachToRecyclerView(binding.recyclerFav)
    }




}