package com.example.atividadelogin.activities

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.atividadelogin.R

class ResultRegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result_register)

        val txtName = findViewById<TextView>(R.id.nameText)
        val txtCpf = findViewById<TextView>(R.id.cpfText)

        txtName.text = getString(R.string.txtName) + intent.getStringExtra("name")
        txtCpf.text = getString(R.string.txtCpf) + intent.getStringExtra("cpf")

        supportActionBar?.title = getString(R.string.resultRegister)
    }
}
