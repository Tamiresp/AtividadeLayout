package com.example.atividadelogin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView

class ResultLoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result_login)

        val txtLogin = findViewById<TextView>(R.id.loginText)
        val txtPassword = findViewById<TextView>(R.id.passwordText)

        txtLogin.text = getString(R.string.txtLogin) +intent.getStringExtra("login")
        txtPassword.text = getString(R.string.txtSenha) + intent.getStringExtra("password")

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.title = "Result Login"
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                startActivity(Intent(this, LoginActivity::class.java))
                finishAffinity()
            }
            else -> {
            }
        }
        return true
    }
}
