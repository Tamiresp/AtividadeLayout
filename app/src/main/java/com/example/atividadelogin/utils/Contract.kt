package com.example.atividadelogin.utils

import android.provider.BaseColumns

object Contract {
    object TaskEntry : BaseColumns{
        const val TABLE_NAME = "todo"
        const val ID = "id"
        const val COLUMN_NAME_TODO = "text"
        const val COLUMN_DATE = "date"
        const val ID_USER = "id_user"

    }

    object LoginEntry : BaseColumns {
        const val TABLE_NAME = "login"
        const val COLUMN_NAME_ID = "id"
        const val COLUMN_NAME_LOGIN = "login_name"
        const val COLUMN_NAME_PASSWORD = "password"
        const val COLUMN_NAME_CPF = "cpf"
        const val COLUMN_NAME_EMAIL = "email"
    }
}