package com.hanaahany.weatherapp.services.alarm

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.media.MediaPlayer
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.app.NotificationManagerCompat
import com.example.noaa.services.notification.NotificationChannelHelper.createNotification
import com.hanaahany.weatherapp.R
import com.hanaahany.weatherapp.Utils.Constants
import com.hanaahany.weatherapp.Utils.Permission
import com.hanaahany.weatherapp.dp.LocalSource
import com.hanaahany.weatherapp.model.Alarm
import com.hanaahany.weatherapp.services.network.RemoteSource
import com.hanaahany.weatherapp.services.sharedpref.SettingSharedPrefrences
import kotlinx.coroutines.*

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val item = intent?.getSerializableExtra(Constants.ALARM_ITEM) as Alarm

        //this message is shown when api response have no alerts
        var messageFromApi = "The weather has cleared up and conditions are now good"

        CoroutineScope(Dispatchers.IO).launch {
            try {
                context?.let {
                    LocalSource(context).deleteAlarm(item)

                    if (isNotificationEnabled(it)) {
                        Log.w(Constants.locationTag, "onReceive: here2" )
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
                                else -> {}
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



    private fun createAlertDialog(context: Context, messageFromApi: String, zoneName: String) {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.alert_dialog_alarm, null)
        val dialogMessage = dialogView.findViewById<TextView>(R.id.alert_description)
        val zoneNameTV = dialogView.findViewById<TextView>(R.id.zoneName)
        val dialogOkButton = dialogView.findViewById<Button>(R.id.alert_stop)


        dialogMessage.text = messageFromApi
        zoneNameTV.text = zoneName

        val mediaPlayer = MediaPlayer.create(context, R.raw.alert)

        val builder = AlertDialog.Builder(context, R.style.MyCustomAlertDialogStyle)
        builder.setView(dialogView)

        val dialog = builder.create()
        dialog.setCancelable(false)
        dialog.setOnShowListener {
            mediaPlayer.start()
            mediaPlayer.isLooping = true
        }

        val window = dialog.window
        window?.setType(WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY)
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

    private fun isNotificationEnabled(context: Context) = SettingSharedPrefrences.getInstance(context)
        .readStringSettings(Constants.NOTIFICATION) != Constants.DISABLE

}