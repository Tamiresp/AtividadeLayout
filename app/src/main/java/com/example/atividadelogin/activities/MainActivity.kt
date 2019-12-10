package com.example.atividadelogin.activities

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.atividadelogin.R
import com.example.atividadelogin.service.MyService
import com.example.atividadelogin.service.NotificationDate

class MainActivity : AppCompatActivity() {
    private val mDateStateChanged = NotificationDate()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val btnRegister = findViewById<Button>(R.id.btnRegister)

        btnLogin.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        btnRegister.setOnClickListener{
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        val service = Intent(this, MyService::class.java)
        service.putExtra("state", intent.getBooleanExtra("state", false))

        registerReceiver(mDateStateChanged, IntentFilter(Intent.ACTION_DATE_CHANGED))
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(mDateStateChanged)
    }
}
