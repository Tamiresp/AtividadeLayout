package com.example.atividadelogin.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.atividadelogin.R

class DistracaoAdapter (private val items: MutableList<String>) : RecyclerView.Adapter<MyViewHolderDistracao>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolderDistracao {
        val viewHolder = LayoutInflater.from(parent.context).inflate(R.layout.item_distracao, parent, false)
        return MyViewHolderDistracao(viewHolder)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: MyViewHolderDistracao, position: Int) {
        val item: String = items[position]
        holder.item.text = item
    }

    fun addItem(item: String){
        items.add(item)
        notifyItemInserted(itemCount)
    }
}
class MyViewHolderDistracao (itemView: View): RecyclerView.ViewHolder(itemView){
    val item: TextView = itemView.findViewById(R.id.item)
}