package com.example.atividadelogin.activities

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.atividadelogin.R

class ResultLoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result_login)

        val txtLogin = findViewById<TextView>(R.id.loginText)
        val txtPassword = findViewById<TextView>(R.id.passwordText)

        txtLogin.text = getString(R.string.txtLogin) +intent.getStringExtra("login")
        txtPassword.text = getString(R.string.txtSenha) + intent.getStringExtra("password")

        supportActionBar?.title = getString(R.string.resultLogin)
    }
}
