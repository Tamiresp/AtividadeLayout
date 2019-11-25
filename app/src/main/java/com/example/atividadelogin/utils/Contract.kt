package com.example.atividadelogin.utils

import android.provider.BaseColumns

object Contract {
    object UserEntry : BaseColumns{
        const val TABLE_NAME = "todo"
        const val ID = "id"
        const val COLUMN_NAME_TITLE = "text"

    }

    object LoginEntry : BaseColumns {
        const val TABLE_NAME = "login"
        const val COLUMN_NAME_ID = "id"
        const val COLUMN_NAME_LOGIN = "login_name"
        const val COLUMN_NAME_PASSWORD = "password"
    }
}