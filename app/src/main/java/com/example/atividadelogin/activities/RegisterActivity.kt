package com.example.atividadelogin.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.atividadelogin.R
import com.example.atividadelogin.data.DatabaseLogin
import com.example.atividadelogin.utils.Contract
import com.google.android.material.snackbar.Snackbar

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val btnCancel = findViewById<Button>(R.id.cancelRegister)
        val btnEnter = findViewById<Button>(R.id.enterRegister)
        val editName = findViewById<EditText>(R.id.name)
        val editCpf = findViewById<EditText>(R.id.cpf)
        val editLogin = findViewById<EditText>(R.id.loginRegister)
        val editPassword = findViewById<EditText>(R.id.passwordRegister)
        val dbHelper = DatabaseLogin(this)

        val erro = intent.getStringExtra("erro")


        btnCancel.setOnClickListener {
            finish()
        }

        Snackbar.make(findViewById(R.id.register_layout), erro, Snackbar.LENGTH_LONG).show()

        btnEnter.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java).apply {
                putExtra("loginRegister", editLogin.editableText.toString())
                putExtra("passwordRegister", editPassword.editableText.toString())
            }
            if (TextUtils.isEmpty(editName.text) || TextUtils.isEmpty(editCpf.text)
                || TextUtils.isEmpty(editLogin.text) || TextUtils.isEmpty(editLogin.text)){
                Snackbar.make(findViewById(R.id.register_layout), R.string.campos, Snackbar.LENGTH_LONG).show()
            }

            val itemLogin = mutableListOf<String>()
            with(dbHelper.getLogs()) {
                while (moveToNext()) {
                    val login = getString(getColumnIndexOrThrow(Contract.LoginEntry.COLUMN_NAME_LOGIN))
                    itemLogin.add(login)
                }
            }


            if (!itemLogin.contains(editLogin.text.toString())){
                dbHelper.insertLog(editLogin.text.toString(), editPassword.text.toString())
                Snackbar.make(findViewById(R.id.register_layout), R.string.sucesso, Snackbar.LENGTH_LONG).show()
                startActivity(intent)
            } else {
                Snackbar.make(findViewById(R.id.register_layout), R.string.cadastrado, Snackbar.LENGTH_LONG).show()
            }

        }

        editName.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                (this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(
                    editName.windowToken, 0)
            }
        }

        editCpf.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                (this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(
                    editCpf.windowToken, 0)
            }
        }

        editLogin.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                (this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(
                    editCpf.windowToken, 0)
            }
        }

        editPassword.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                (this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(
                    editCpf.windowToken, 0)
            }
        }
        supportActionBar?.title = getString(R.string.register)
    }

}
