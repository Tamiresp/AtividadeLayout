package com.example.atividadelogin.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View.OnFocusChangeListener
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.atividadelogin.R
import com.example.atividadelogin.data.DatabaseLogin
import com.example.atividadelogin.utils.Contract


class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val btnCancel = findViewById<Button>(R.id.cancelLogin)
        val btnEnter = findViewById<Button>(R.id.enterLogin)
        val editLogin = findViewById<EditText>(R.id.login)
        val editPassword = findViewById<EditText>(R.id.password)
        val dbHelper = DatabaseLogin(this)

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
                //putExtra("password", editPassword.editableText.toString())
            }
            if (TextUtils.isEmpty(editLogin.text) || TextUtils.isEmpty(editPassword.text)) {
//                val builder1 = AlertDialog.Builder(this)
//                builder1.setCancelable(false)
//                builder1.setMessage(R.string.campos)
//                builder1.setPositiveButton(R.string.ok) { dialog, _ ->
//                    dialog.dismiss()
//
//                    val dialog = builder1.create()
//                    dialog.show()
//                }
                // TODO tratar erro
                Toast.makeText(this, "campos", Toast.LENGTH_LONG).show()
            } else {
                if (!itemLogin.contains(editLogin.text.toString()) && !itemPass.contains(editPassword.text.toString())) {
//                    val builder = AlertDialog.Builder(this)
//                    builder.setCancelable(false)
//                    builder.setMessage(R.string.no_user)
//                    builder.setPositiveButton(R.string.ok) { dialog, _ ->
//                        dialog.dismiss()
//
//                        val dialog = builder.create()
//                        dialog.show()
//                    }
                    Toast.makeText(this, "usuario", Toast.LENGTH_LONG).show()
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
