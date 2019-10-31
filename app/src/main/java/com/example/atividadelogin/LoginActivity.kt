package com.example.atividadelogin

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.MenuItem
import android.view.View.OnFocusChangeListener
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity


class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val btnCancel = findViewById<Button>(R.id.cancelLogin)
        val btnEnter = findViewById<Button>(R.id.enterLogin)
        val editLogin = findViewById<EditText>(R.id.login)
        val editPassword = findViewById<EditText>(R.id.password)

        btnCancel.setOnClickListener {
            finish()
        }
        btnEnter.setOnClickListener {
            val intent = Intent(this, ResultLoginActivity::class.java).apply {
                putExtra("login", editLogin.editableText.toString())
                putExtra("password", editPassword.editableText.toString())
            }
            if (TextUtils.isEmpty(editLogin.text) || TextUtils.isEmpty(editPassword.text)){
                val builder = AlertDialog.Builder(this)
                builder.setCancelable(false)
                builder.setMessage(R.string.campos)
                builder.setPositiveButton(R.string.ok) { dialog, _ ->
                    dialog.dismiss()
                }
                val dialog = builder.create()
                dialog.show()
            } else{
                startActivity(intent)
            }
        }

        editLogin.onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                (this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(
                    editLogin.windowToken, 0)
            }
        }

        editPassword.onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                (this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(
                    editPassword.windowToken, 0)
            }
        }

        supportActionBar?.title = getString(R.string.login)
    }

}
