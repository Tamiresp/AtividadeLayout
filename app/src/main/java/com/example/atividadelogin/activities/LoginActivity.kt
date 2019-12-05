package com.example.atividadelogin.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View.OnFocusChangeListener
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.atividadelogin.R
import com.example.atividadelogin.data.DatabaseLogin
import com.example.atividadelogin.utils.Contract
import com.google.android.material.snackbar.Snackbar


class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val btnCancel = findViewById<Button>(R.id.cancelLogin)
        val btnEnter = findViewById<Button>(R.id.enterLogin)
        val editLogin = findViewById<EditText>(R.id.login)
        val editPassword = findViewById<EditText>(R.id.password)
        val dbHelper = DatabaseLogin(this)

        val login = intent.getStringExtra("loginRegister")
        val password = intent.getStringExtra("passwordRegister")

        editLogin.setText(login)
        editPassword.setText(password)

        val itemLogin = mutableListOf<String>()
        val itemPass = mutableListOf<String>()
        with(dbHelper.getLogs()) {
            while (moveToNext()) {
                val login = getString(getColumnIndexOrThrow(Contract.LoginEntry.COLUMN_NAME_LOGIN))
                val password = getString(getColumnIndexOrThrow(Contract.LoginEntry.COLUMN_NAME_PASSWORD))
                itemLogin.add(login)
                itemPass.add(password)
            }
        }

        btnCancel.setOnClickListener {
            finish()
        }

        btnEnter.setOnClickListener {
            val intent = Intent(this, ListActivity::class.java).apply {
                putExtra("login", editLogin.editableText.toString())
            }
            if (TextUtils.isEmpty(editLogin.text) || TextUtils.isEmpty(editPassword.text)) {
                Snackbar.make(findViewById(R.id.login_layout), R.string.campos, Snackbar.LENGTH_LONG).show()
            } else {
                if (!itemLogin.contains(editLogin.text.toString())) {
                    Snackbar.make(findViewById(R.id.login_layout), R.string.no_user, Snackbar.LENGTH_LONG).show()
                } else if (!itemPass.contains(editPassword.text.toString())){
                        Snackbar.make(findViewById(R.id.login_layout), R.string.no_pass, Snackbar.LENGTH_LONG).show()
                } else {
                    startActivity(intent)
                }
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
