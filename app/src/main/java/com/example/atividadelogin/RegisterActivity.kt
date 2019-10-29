package com.example.atividadelogin

import android.app.Dialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val btnCancel = findViewById<Button>(R.id.cancelRegister)
        val btnEnter = findViewById<Button>(R.id.enterRegister)
        val editName = findViewById<EditText>(R.id.name)
        val editCpf = findViewById<EditText>(R.id.cpf)

        btnCancel.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        btnEnter.setOnClickListener {
            val intent = Intent(this, ResultRegisterActivity::class.java).apply {
                putExtra("name", editName.editableText.toString())
                putExtra("cpf", editCpf.editableText.toString())
            }
            if (TextUtils.isEmpty(editName.text) || TextUtils.isEmpty(editCpf.text)){
                val builder = AlertDialog.Builder(this)
                builder.setCancelable(false)
                builder.setMessage(R.string.campos)
                builder.setPositiveButton("ok") { dialog, _ ->
                    dialog.dismiss()
                }
                val dialog = builder.create()
                dialog.show()
            } else{
                startActivity(intent)
            }
        }

        editName.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                (this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(
                    editName.windowToken, 0
                )
            }
        }

        editCpf.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                (this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(
                    editCpf.windowToken, 0
                )
            }
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.title = "Register"
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                startActivity(Intent(this, MainActivity::class.java))
                finishAffinity()
            }
            else -> {
            }
        }
        return true
    }
}
