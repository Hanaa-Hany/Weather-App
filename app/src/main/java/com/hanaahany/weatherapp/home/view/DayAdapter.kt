package com.hanaahany.weatherapp.home.view

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.bumptech.glide.Glide
import com.hanaahany.weatherapp.Utils.Constants
import com.hanaahany.weatherapp.databinding.WeekTempLayoutBinding
import com.hanaahany.weatherapp.model.DailyWeather
import com.hanaahany.weatherapp.network.sharedpref.SettingSharedPrefrences

class DayAdapter(var context: Context, var list: List<DailyWeather>) :
    Adapter<DayAdapter.DayViewHolder>() {
    private lateinit var binding: WeekTempLayoutBinding

    class DayViewHolder(var binding: WeekTempLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayViewHolder {
        context = parent.context
        val inflater =
            parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = WeekTempLayoutBinding.inflate(inflater, parent, false)
        return DayViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DayViewHolder, position: Int) {
        val resp = list.get(position)
        holder.binding.tvDayHumidityWeekTempLayout.text = resp.humidity.toString()
        holder.binding.tvTempWeekTempLayout.text =Constants.writeDegree(context,resp.temp.day.toInt().toString())
        holder.binding.tvTempNigtWeekTempLayout.text = Constants.writeDegree(context,resp.temp.night.toInt().toString())
        Glide.with(context).load("https://openweathermap.org/img/wn/${resp.weather[0].icon}@2x.png")
            .into(
                holder.binding.imageDayIconWeekTempLayout
            )
        if (SettingSharedPrefrences.getInstance(context)
                .readStringSettings(Constants.LANGUAGE) == "en"
        ) {
            holder.binding.tvDayNameWeekTempLayout.text = Constants.getDateDay(resp.dt, "en")
            Log.i(
                Constants.locationTag,
                SettingSharedPrefrences.getInstance(context).readStringSettings("lang")
            )
        } else {
            holder.binding.tvDayNameWeekTempLayout.text = Constants.getDateDay(resp.dt, "ar")

        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}