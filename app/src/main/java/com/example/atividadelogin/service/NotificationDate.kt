package com.example.atividadelogin.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log

class NotificationDate: BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {

        if (intent.action.equals(Intent.ACTION_AIRPLANE_MODE_CHANGED)) {
            Log.d("DateStateChanged", "Mudou estado da data, estado: " +
                    "${intent.getBooleanExtra("state", false)}")

            val service = Intent(context, MyService::class.java)
            service.putExtra("state", intent.getBooleanExtra("state", false))

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(service)
            } else {
                context.startService(service)
            }
        } else {
            Log.e("DateStateChanged", "Aconteceu algum problema, Action ${intent.action}")
        }

    }

}