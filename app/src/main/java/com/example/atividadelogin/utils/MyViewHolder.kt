package com.example.atividadelogin.utils

import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.atividadelogin.R

class MyViewHolder (itemView: View): RecyclerView.ViewHolder(itemView){
    val btnDelete: ImageButton = itemView.findViewById(R.id.btnDelete)
    val btnEdit: CardView = itemView.findViewById(R.id.card_update)
    val title: TextView = itemView.findViewById(R.id.tvItem)
    val date: TextView = itemView.findViewById(R.id.tvTime)

}