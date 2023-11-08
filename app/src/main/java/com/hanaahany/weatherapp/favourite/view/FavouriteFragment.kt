package com.hanaahany.weatherapp.favourite.view

import android.opengl.Visibility
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.hanaahany.weatherapp.R
import com.hanaahany.weatherapp.databinding.FavLayoutBinding
import com.hanaahany.weatherapp.databinding.FragmentFavouriteBinding
import com.hanaahany.weatherapp.maps.MapsFragment


class FavouriteFragment : Fragment() {

    private lateinit var binding: FragmentFavouriteBinding
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
        binding.fabFav.setOnClickListener{
            requireFragmentManager().beginTransaction().replace(R.id.homeFragment,MapsFragment()).commit()
//            if(MapsFragment().view.id==R.id.btn_save){
//                MapsFragment().id=isVisible(1)
//            }
        }
    }



}