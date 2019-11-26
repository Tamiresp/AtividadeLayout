package com.example.atividadelogin.data

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import android.util.Log
import com.example.atividadelogin.utils.Contract

class DatabaseUser(context: Context) : SQLiteOpenHelper(context, "todo.db", null, 1) {

    private val SQL_CREATE_ENTRIES =
        "CREATE TABLE ${Contract.UserEntry.TABLE_NAME} (" +
                "${Contract.UserEntry.ID} INTEGER NOT NULL," +
                "FOREIGN KEY ${Contract.UserEntry.ID} REFERENCES ${Contract.LoginEntry.TABLE_NAME}(${Contract.LoginEntry.COLUMN_NAME_ID})," +
                "${Contract.UserEntry.COLUMN_NAME_TITLE} TEXT)"

    private val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS ${Contract.UserEntry.TABLE_NAME}"

    fun insertLog(text: String) {
        val values = ContentValues()
        values.put(Contract.UserEntry.COLUMN_NAME_TITLE, text)
        writableDatabase.insert(Contract.UserEntry.TABLE_NAME, null, values)
    }

    fun getLogs(): Cursor {
        return readableDatabase
            .query(Contract.UserEntry.TABLE_NAME, arrayOf(Contract.UserEntry.ID, Contract.UserEntry.COLUMN_NAME_TITLE
                ), null, null, null, null, null)
    }

    fun getLog(id: Int) : Cursor {
        return readableDatabase
            .query(Contract.UserEntry.TABLE_NAME, arrayOf(Contract.UserEntry.ID, Contract.UserEntry.COLUMN_NAME_TITLE),
                "${Contract.UserEntry.ID}=${id}", null, null, null, null)
    }

    fun updateLog(id: Int, text: String) {
        val values = ContentValues()
        values.put(Contract.UserEntry.ID, id)
        values.put(Contract.UserEntry.COLUMN_NAME_TITLE, text)

        writableDatabase.update(Contract.UserEntry.TABLE_NAME, values, "${Contract.UserEntry.ID}=${id}", null)
    }

    fun removeLog(id: Int): Int {
        return writableDatabase.delete(Contract.UserEntry.TABLE_NAME, "${Contract.UserEntry.ID}=${id}", null)
    }

    override fun onCreate(db: SQLiteDatabase?) {
        Log.d("MyDatabase", "Creating: ${Contract.UserEntry.TABLE_NAME}")
        db?.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }
}