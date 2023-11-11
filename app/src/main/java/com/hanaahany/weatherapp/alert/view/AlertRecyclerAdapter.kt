package com.hanaahany.weatherapp.alert.view

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hanaahany.weatherapp.Utils.Constants
import com.hanaahany.weatherapp.databinding.ItemAlertBinding
import com.hanaahany.weatherapp.model.Alarm

class AlertRecyclerAdapter :
    ListAdapter<Alarm, AlertRecyclerAdapter.AlertViewHolder>(RecyclerDiffUtilAlarmItem()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlertViewHolder {
        val inflater: LayoutInflater =
            parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val binding = ItemAlertBinding.inflate(inflater, parent, false)
        return AlertViewHolder(binding)
    }


    override fun onBindViewHolder(holder: AlertViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.onBind(currentItem)
    }

    inner class AlertViewHolder(private val binding: ItemAlertBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(currentItem: Alarm) {
            binding.apply {

                tvFromDate.text =
                    Constants.formatLongToAnyString(currentItem.time, "dd MMM yyyy")

                tvFromTime.text =
                    Constants.formatLongToAnyString(currentItem.time, "hh:mm a")

            }
        }
    }
}

class RecyclerDiffUtilAlarmItem : DiffUtil.ItemCallback<Alarm>() {
    override fun areItemsTheSame(oldItem: Alarm, newItem: Alarm): Boolean {
        return oldItem.time == newItem.time
    }

    override fun areContentsTheSame(oldItem: Alarm, newItem: Alarm): Boolean {
        return oldItem == newItem
    }
}