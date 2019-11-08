package com.example.atividadelogin.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.atividadelogin.R
import com.example.atividadelogin.utils.MyViewHolder
import com.example.atividadelogin.utils.User

class MyAdapter (private val users: MutableList<User>): RecyclerView.Adapter<MyViewHolder>() {
    override fun getItemCount(): Int {
        return users.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val user: User = users[position]
        holder.title.text = user.todo
        holder.btnDelete.setOnClickListener {
            users.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, this.itemCount)
        }

    }

    fun addTask(user: User){
        users.add(user)
        notifyItemInserted(itemCount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val viewHolder = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return MyViewHolder(viewHolder)
    }

}
