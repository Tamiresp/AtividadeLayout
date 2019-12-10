package com.example.atividadelogin.data

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.atividadelogin.utils.Contract

class DatabaseLogin (context: Context) : SQLiteOpenHelper(context, "login.db", null, 3) {
    private val SQL_CREATE_ENTRIES_LOGIN =
        "CREATE TABLE ${Contract.LoginEntry.TABLE_NAME} (" +
                "${Contract.LoginEntry.COLUMN_NAME_ID} INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "${Contract.LoginEntry.COLUMN_NAME_LOGIN} TEXT," +
                "${Contract.LoginEntry.COLUMN_NAME_EMAIL} TEXT," +
                "${Contract.LoginEntry.COLUMN_NAME_CPF} TEXT," +
                "${Contract.LoginEntry.COLUMN_NAME_PASSWORD} TEXT)"

    private val SQL_DELETE_ENTRIES_LOGIN = "DROP TABLE IF EXISTS ${Contract.LoginEntry.TABLE_NAME}"

    override fun onCreate(db: SQLiteDatabase?) {
        Log.d("MyDatabase", "Creating: ${Contract.LoginEntry.TABLE_NAME}")
        db?.execSQL(SQL_CREATE_ENTRIES_LOGIN)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL(SQL_DELETE_ENTRIES_LOGIN)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    fun insertLog(login: String, password: String, cpf: String, email: String) {
        val values = ContentValues()
        values.put(Contract.LoginEntry.COLUMN_NAME_LOGIN, login)
        values.put(Contract.LoginEntry.COLUMN_NAME_PASSWORD, password)
        values.put(Contract.LoginEntry.COLUMN_NAME_CPF, cpf)
        values.put(Contract.LoginEntry.COLUMN_NAME_EMAIL, email)
        writableDatabase.insert(Contract.LoginEntry.TABLE_NAME, null, values)
    }

    fun getLogs(): Cursor {
        return readableDatabase
            .query(Contract.LoginEntry.TABLE_NAME, arrayOf(Contract.LoginEntry.COLUMN_NAME_ID,
                Contract.LoginEntry.COLUMN_NAME_LOGIN, Contract.LoginEntry.COLUMN_NAME_PASSWORD),
                null, null, null, null, null)
    }

    fun getLog(login: String) : Cursor {
        return readableDatabase
            .query(Contract.LoginEntry.TABLE_NAME, arrayOf(Contract.LoginEntry.COLUMN_NAME_ID,
                Contract.LoginEntry.COLUMN_NAME_LOGIN, Contract.LoginEntry.COLUMN_NAME_PASSWORD),
                "${Contract.LoginEntry.COLUMN_NAME_LOGIN}= '${login}'", null,
                null, null, null)
    }

    fun updateLog(id: Int, login: String, password: String) {
        val values = ContentValues()
        values.put(Contract.LoginEntry.COLUMN_NAME_ID, id)
        values.put(Contract.LoginEntry.COLUMN_NAME_LOGIN, login)
        values.put(Contract.LoginEntry.COLUMN_NAME_PASSWORD, password)

        writableDatabase.update(Contract.LoginEntry.TABLE_NAME, values,
            "${Contract.LoginEntry.COLUMN_NAME_ID}=${id}", null)
    }

    fun removeLog(id: Int): Int {
        return writableDatabase.delete(Contract.LoginEntry.TABLE_NAME,
            "${Contract.LoginEntry.COLUMN_NAME_ID}=${id}", null)
    }
}