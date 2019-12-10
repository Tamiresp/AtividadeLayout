package com.example.atividadelogin.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.atividadelogin.R
import com.example.atividadelogin.requests.entity_request.ArticlesRequest

class DistracaoAdapter (private val items: MutableList<ArticlesRequest>) : RecyclerView.Adapter<MyViewHolderDistracao>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolderDistracao {
        val viewHolder = LayoutInflater.from(parent.context).inflate(R.layout.item_distracao, parent, false)
        return MyViewHolderDistracao(viewHolder)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: MyViewHolderDistracao, position: Int) {
        val item: ArticlesRequest = items[position]
        holder.title.text = item.title
        holder.author.text = item.author
        holder.description.text = item.description
    }

    fun addItem(item: ArticlesRequest){
        items.add(item)
        notifyItemInserted(itemCount)
    }
}
class MyViewHolderDistracao (itemView: View): RecyclerView.ViewHolder(itemView){
    val title: TextView = itemView.findViewById(R.id.tvTitle)
    val author: TextView = itemView.findViewById(R.id.tvAuthor)
    val description: TextView = itemView.findViewById(R.id.tvDescription)
}