package com.example.atividadelogin.service

import android.app.IntentService
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.atividadelogin.R
import com.example.atividadelogin.activities.MainActivity

class MyService : IntentService("MyService"){
    override fun onHandleIntent(intent: Intent?) {
//        Log.d("MyService", "O servico startou")
//        val state = intent!!.getBooleanExtra("state", false)
        callAirPlaneNotification(this)
    }

    private fun callAirPlaneNotification(context: Context) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        //intent.putExtra("state", state)

        val pedingIntent = PendingIntent.getActivity(context, 1234,
            intent, PendingIntent.FLAG_UPDATE_CURRENT)

        val builder = NotificationCompat.Builder(context, "MYAPP")
        builder.setSmallIcon(R.mipmap.ic_inicial_foreground)
        builder.setContentTitle("My app notification")
        builder.setContentText("Acesse o app " + getString(R.string.app_name))
        builder.priority = NotificationCompat.PRIORITY_HIGH
        builder.setAutoCancel(true)
        builder.setContentIntent(pedingIntent)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel("MYAPP",
                "My Application", NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(notificationChannel)
        }

        val notification = builder.build()
        notificationManager.notify(1234, notification)
    }
}