package com.hanaahany.weatherapp.home.view

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hanaahany.weatherapp.Utils.Constants
import com.hanaahany.weatherapp.databinding.HourlyTempLayoutBinding
import com.hanaahany.weatherapp.services.model.HourlyWeather
import com.hanaahany.weatherapp.services.sharedpref.SettingSharedPrefrences

class HourAdapter(var context: Context, var list: List<HourlyWeather>) :
    RecyclerView.Adapter<HourAdapter.HourViewHolder>() {
    private lateinit var binding: HourlyTempLayoutBinding

    class HourViewHolder(var binding: HourlyTempLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourViewHolder {
        context = parent.context
        val inflater =
            parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = HourlyTempLayoutBinding.inflate(inflater, parent, false)
        return HourViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: HourViewHolder, position: Int) {
        val resp = list.get(position)
        holder.binding.tvDayHumidityHourTempLayout.text = resp.humidity.toString()
        holder.binding.tvTempHourTempLayout.text = Constants.writeDegree(context,resp.temp.toInt().toString())

        if (SettingSharedPrefrences.getInstance(context)
                .readStringSettings(Constants.LANGUAGE) == "ar"
        ) {
            holder.binding.tvHourTempLayout.text = Constants.getTimeHour(resp.dt, "ar")
        } else {
            holder.binding.tvHourTempLayout.text = Constants.getTimeHour(resp.dt, "en")

        }

        Glide.with(context).load("https://openweathermap.org/img/wn/${resp.weather[0].icon}@2x.png")
            .into(holder.binding.imageDayIconHourTempLayout)


    }

    override fun getItemCount(): Int {
        return list.size
    }
}