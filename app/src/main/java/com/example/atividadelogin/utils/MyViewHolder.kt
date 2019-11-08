package com.example.atividadelogin.utils

import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.atividadelogin.R

class MyViewHolder (itemView: View): RecyclerView.ViewHolder(itemView){
    val btnDelete: ImageButton = itemView.findViewById(R.id.btnDelete)
    val btnEdit: ImageButton = itemView.findViewById(R.id.btnEdit)
    val title: TextView = itemView.findViewById(R.id.tvItem)

}