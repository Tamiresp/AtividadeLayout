package com.example.atividadelogin.activities

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.atividadelogin.R
import com.example.atividadelogin.data.DatabaseLogin
import com.example.atividadelogin.utils.Contract
import javax.net.ssl.HandshakeCompletedEvent

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


        btnCancel.setOnClickListener {
            finish()
        }

        btnEnter.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java).apply {
                putExtra("name", editName.editableText.toString())
                putExtra("cpf", editCpf.editableText.toString())
                putExtra("loginRegister", editLogin.editableText.toString())
                putExtra("passwordRegister", editPassword.editableText.toString())
            }
            if (TextUtils.isEmpty(editName.text) || TextUtils.isEmpty(editCpf.text)
                || TextUtils.isEmpty(editLogin.text) || TextUtils.isEmpty(editLogin.text)){
                val builder = AlertDialog.Builder(this)
                builder.setCancelable(false)
                builder.setMessage(R.string.campos)
                builder.setPositiveButton(R.string.ok) { dialog, _ ->
                    dialog.dismiss()
                }
                val dialog = builder.create()
                dialog.show()
            } else{
                Toast.makeText(this, R.string.sucesso, Toast.LENGTH_LONG).show()

                val db = dbHelper.writableDatabase

                val values = ContentValues().apply {
                    put(Contract.LoginEntry.COLUMN_NAME_LOGIN, editLogin.text.toString())
                    put(Contract.LoginEntry.COLUMN_NAME_PASSWORD, editPassword.text.toString())

                }
                val newRowId = db?.insert(Contract.LoginEntry.TABLE_NAME, null, values)
                startActivity(intent)
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

        val db = dbHelper.readableDatabase

        val projection = arrayOf(Contract.LoginEntry.COLUMN_NAME_ID, Contract.LoginEntry.COLUMN_NAME_LOGIN, Contract.LoginEntry.COLUMN_NAME_PASSWORD)

        val selection = "${Contract.LoginEntry.COLUMN_NAME_LOGIN} = ?"
        val selectionArgs = arrayOf("My Title")

        val sortOrder = "${Contract.LoginEntry.COLUMN_NAME_LOGIN} DESC"


    }

}
