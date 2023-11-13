package com.hanaahany.weatherapp.services.alarm

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.media.MediaPlayer
import android.os.Build
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.app.NotificationManagerCompat
import com.example.noaa.services.notification.NotificationChannelHelper
import com.hanaahany.weatherapp.R
import com.hanaahany.weatherapp.Utils.Constants
import com.hanaahany.weatherapp.Utils.Permission
import com.hanaahany.weatherapp.services.dp.LocalSource
import com.hanaahany.weatherapp.services.model.Alarm
import com.hanaahany.weatherapp.services.network.WeatherClient
import com.hanaahany.weatherapp.services.sharedpref.SettingSharedPrefrences
import kotlinx.coroutines.*

class AlarmReceiver : BroadcastReceiver() {


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onReceive(context: Context?, intent: Intent?) {
        val item = intent?.getSerializableExtra(Constants.ALARM_ITEM) as Alarm

        //this message is shown when api response have no alerts
        var messageFromApi = "The weather has cleared up and conditions are now good"

        CoroutineScope(Dispatchers.IO).launch {
            try {
                context?.let {
                    LocalSource(context).deleteAlarm(item)

                    val mes = getAlertMessageFromApi(it, item)

                    if (mes != "null") {
                        messageFromApi = mes
                    }
                    if (isNotificationEnabled(it)) {
                        Log.w(Constants.locationTag, "onReceive: here2")

                        withContext(Dispatchers.Main) {
                            when (item.kind) {
                                Constants.NOTIFICATION -> createNotification(
                                    it,
                                    messageFromApi,
                                    item.zoneName
                                )
                                Constants.ALERT -> createAlertDialog(
                                    it,
                                    messageFromApi,
                                    item.zoneName
                                )
                            }
                        }
                    }
                }
            } catch (_: Exception) {
            } finally {
                cancel()
            }
        }
    }


    @SuppressLint("MissingPermission")
    fun createNotification(context: Context, messageFromApi: String, zoneName: String) {
        val builder = NotificationChannelHelper.createNotification(
            context, messageFromApi, zoneName
        )
        with(NotificationManagerCompat.from(context)) {
            notify(Constants.NOTIFICATION_ID, builder.build())
        }
        val mediaPlayer = MediaPlayer.create(context, R.raw.pop_up)
        mediaPlayer.start()
        mediaPlayer.setOnCompletionListener { mp ->
            mp.release()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createAlertDialog(context: Context, messageFromApi: String, zoneName: String) {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.alert_dialog_alarm, null)
        val dialogMessage = dialogView.findViewById<TextView>(R.id.alert_description)
        val zoneNameTV = dialogView.findViewById<TextView>(R.id.zoneName)
        val dialogOkButton = dialogView.findViewById<Button>(R.id.alert_stop)
        Toast.makeText(context, "Alert Dialog", Toast.LENGTH_SHORT).show()

        dialogMessage.text = messageFromApi
        zoneNameTV.text = zoneName

        val mediaPlayer = MediaPlayer.create(context, R.raw.pop_up)

        val builder = AlertDialog.Builder(context, R.style.MyCustomAlertDialogStyle)
        builder.setView(dialogView)

        val dialog = builder.create()
        dialog.setCancelable(false)
        dialog.setOnShowListener {
            mediaPlayer.start()
            mediaPlayer.isLooping = true
        }

        val window = dialog.window
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            window?.setType(WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY)
        }
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window?.setGravity(Gravity.TOP)

        dialog.show()

        CoroutineScope(Dispatchers.IO).launch {
            try {
                delay(15000)
                withContext(Dispatchers.Main) {
                    if (dialog.isShowing) {
                        dialog.dismiss()
                        createNotification(context, messageFromApi, zoneName)
                    }
                }
            } catch (_: Exception) {
            } finally {
                cancel()
            }
        }

        dialogOkButton.setOnClickListener {
            dialog.dismiss()
        }

        dialog.setOnDismissListener {
            mediaPlayer.stop()
            mediaPlayer.setOnCompletionListener { mp ->
                mp.release()
            }
        }
    }

    private fun isNotificationEnabled(context: Context) =
        SettingSharedPrefrences.getInstance(context)
            .readStringSettings(Constants.NOTIFICATION) != Constants.DISABLE

    private suspend fun getAlertMessageFromApi(

        context: Context,
        alarmItem: Alarm
    ): String {
        var mes = "null"
        try {
            if (Permission.checkConnection(context)) {


                WeatherClient.makeNetworkCall(
                    alarmItem.latitude,
                    alarmItem.longitude,
                    "metric",
                    "en"
                ).collect {

                    mes = it.alerts.get(0).description
                    Log.i("Alarm",it.lat.toString())
                }
            }
        } catch (_: Exception) {
            mes = "null"
        }
        return mes
    }
}