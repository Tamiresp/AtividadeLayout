package com.example.atividadelogin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView

class ResultRegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result_register)

        val txtName = findViewById<TextView>(R.id.nameText)
        val txtCpf = findViewById<TextView>(R.id.cpfText)

        txtName.text = getString(R.string.txtName) + intent.getStringExtra("name")
        txtCpf.text = getString(R.string.txtCpf) + intent.getStringExtra("cpf")

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.title = "Result Register"
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                startActivity(
                    Intent(this, RegisterActivity::class.java))
                finishAffinity()
            }
        }
        return true
    }
}
