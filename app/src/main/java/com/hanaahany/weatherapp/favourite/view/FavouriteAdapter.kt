package com.hanaahany.weatherapp.favourite.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.hanaahany.weatherapp.Utils.Constants
import com.hanaahany.weatherapp.databinding.FavLayoutBinding
import com.hanaahany.weatherapp.databinding.FragmentMapsBinding
import com.hanaahany.weatherapp.model.Place
import com.hanaahany.weatherapp.model.WeatherResponse

class FavouriteAdapter(var context: Context,private val onClick:(Place)->Unit):
    ListAdapter<Place,FavouriteAdapter.FavViewHolder>(FavDiffUtil()) {
    private lateinit var binding: FavLayoutBinding


    class FavViewHolder(var binding: FavLayoutBinding) : ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavViewHolder {
        binding = FavLayoutBinding.inflate(LayoutInflater.from(context), parent, false)
        return FavViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavViewHolder, position: Int) {
        val listItem = getItem(position)
        holder.binding.tempFavLayout.text =listItem.temp.toString()
            //Constants.writeDegree(context, )

        holder.binding.cityFavLayout.text = listItem.city
        holder.binding.addressFavLayout.text = listItem.cityName
        Glide.with(context).load("https://openweathermap.org/img/wn/${listItem.icon}@2x.png")
            .into(
                holder.binding.imageDayIconFavLayout
            )
        holder.binding.dateFavLayout.text = listItem.date


        holder.itemView.setOnClickListener{
            onClick(listItem)
        }
    }
}
    class FavDiffUtil: DiffUtil.ItemCallback<Place>() {
        override fun areItemsTheSame(oldItem: Place, newItem: Place): Boolean {
            return oldItem===newItem
        }

        override fun areContentsTheSame(oldItem: Place, newItem: Place): Boolean {
            return oldItem==newItem
        }
}
