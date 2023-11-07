package com.hanaahany.weatherapp.home.view

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hanaahany.weatherapp.Utils.Constants
import com.hanaahany.weatherapp.databinding.HourlyTempLayoutBinding
import com.hanaahany.weatherapp.model.HourlyWeather
import com.hanaahany.weatherapp.network.sharedpref.SettingSharedPrefrences

class HourAdapter(var context: Context, var list: List<HourlyWeather>):
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

    override fun onBindViewHolder(holder: HourViewHolder, position: Int) {
        val resp = list.get(position)
        holder.binding.tvDayHumidityHourTempLayout.text=resp.humidity.toString()
        holder.binding.tvTempHourTempLayout.text="${resp.temp.toInt().toString()}c"
        if (SettingSharedPrefrences.getInstance(context).readLanguage("lang")=="en"){
            holder.binding.tvHourTempLayout.text=Constants.getTimeHour(resp.dt,"en")
        }else{
            holder.binding.tvHourTempLayout.text=Constants.getTimeHour(resp.dt,"ar")

        }

        Glide.with(context).load("https://openweathermap.org/img/wn/${resp.weather[0].icon}@2x.png").into(holder.binding.imageDayIconHourTempLayout)


    }

    override fun getItemCount(): Int {
        return list.size
    }
}