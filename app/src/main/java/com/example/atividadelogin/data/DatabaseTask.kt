package com.example.atividadelogin.data

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.atividadelogin.utils.Contract

class DatabaseTask(context: Context) : SQLiteOpenHelper(context, "todo.db", null, 5) {

    private val SQL_CREATE_ENTRIES =
        "CREATE TABLE ${Contract.TaskEntry.TABLE_NAME} (" +
                "${Contract.TaskEntry.ID} INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "${Contract.TaskEntry.COLUMN_NAME_TODO} TEXT," +
                "${Contract.TaskEntry.COLUMN_DATE} TEXT," +
                "${Contract.TaskEntry.ID_USER} INTEGER NOT NULL," +
                "FOREIGN KEY (${Contract.TaskEntry.ID_USER}) REFERENCES ${Contract.LoginEntry.TABLE_NAME}" +
                "(${Contract.LoginEntry.COLUMN_NAME_ID}))"

    private val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS ${Contract.TaskEntry.TABLE_NAME}"

    fun insertLog(text: String, date: String, id: Int) {
        val values = ContentValues()
        values.put(Contract.TaskEntry.COLUMN_NAME_TODO, text)
        values.put(Contract.TaskEntry.COLUMN_DATE, date)
        values.put(Contract.TaskEntry.ID_USER, id)
        writableDatabase.insert(Contract.TaskEntry.TABLE_NAME, null, values)
    }

    fun getLogs(): Cursor {
        return readableDatabase
            .query(Contract.TaskEntry.TABLE_NAME, arrayOf(Contract.TaskEntry.ID,
                Contract.TaskEntry.COLUMN_NAME_TODO, Contract.TaskEntry.COLUMN_DATE),
                null,
                null, null, null, null)
    }

    fun getLog(id: Int) : Cursor {
        return readableDatabase
            .query(Contract.TaskEntry.TABLE_NAME, arrayOf(Contract.TaskEntry.COLUMN_NAME_TODO,
                Contract.TaskEntry.COLUMN_DATE), "${Contract.TaskEntry.ID_USER}=${id}",
                null, null, null, null)
    }

    fun updateLog(id: Int, text: String, date: String) {
        val values = ContentValues()
        values.put(Contract.TaskEntry.ID, id)
        values.put(Contract.TaskEntry.COLUMN_NAME_TODO, text)
        values.put(Contract.TaskEntry.COLUMN_DATE, date)

        writableDatabase.update(Contract.TaskEntry.TABLE_NAME, values,
            "${Contract.TaskEntry.ID}=${id}", null)
    }

    fun removeLog(id: Int): Int {
        return writableDatabase.delete(Contract.TaskEntry.TABLE_NAME,
            "${Contract.TaskEntry.ID}=${id}", null)
    }

    override fun onCreate(db: SQLiteDatabase?) {
        Log.d("MyDatabase", "Creating: ${Contract.TaskEntry.TABLE_NAME}")
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