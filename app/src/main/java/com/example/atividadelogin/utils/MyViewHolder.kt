package com.example.atividadelogin.utils

import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.atividadelogin.R

class MyViewHolder (itemView: View): RecyclerView.ViewHolder(itemView){
    var btnAdd: Button = itemView.findViewById(R.id.btnAdd)
    var title: TextView = itemView.findViewById(R.id.textView)

}