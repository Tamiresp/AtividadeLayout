package com.example.atividadelogin.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.atividadelogin.R
import com.example.atividadelogin.data.DatabaseUser
import com.example.atividadelogin.utils.MyViewHolder
import com.example.atividadelogin.utils.User
import kotlinx.android.synthetic.main.dialog_delete_todo.view.*
import kotlinx.android.synthetic.main.dialog_new_todo.view.*

class MyAdapter (private val users: MutableList<User>): RecyclerView.Adapter<MyViewHolder>() {
    private lateinit var mDialogView: View

    override fun getItemCount(): Int {
        return users.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val user: User = users[position]
        holder.title.text = user.todo
        deleteTask(holder, position)
        updateTask(holder, position)
    }

    private fun deleteTask(holder: MyViewHolder, position: Int){
        holder.btnDelete.setOnClickListener {
            alertDelete(holder, position)
        }

    }

    fun addTask(user: User){
        users.add(user)
        notifyItemInserted(itemCount)
    }

    private fun updateTask(holder: MyViewHolder, position: Int){
        holder.btnEdit.setOnClickListener {
            showUpdateTaskDialog(holder, position)
        }
    }

    private fun showUpdateTaskDialog(holder: MyViewHolder, position: Int) {
        val dbHelper = DatabaseUser(holder.itemView.context)
        mDialogView = LayoutInflater.from(holder.itemView.context).inflate(R.layout.dialog_new_todo, null)
        val mBuilder = AlertDialog.Builder(holder.itemView.context)
            .setView(mDialogView)
            .setTitle(R.string.txtTodoupdate)

        val  mAlertDialog = mBuilder.show()

        mDialogView.dialogAddBtn.setOnClickListener {
            mAlertDialog.dismiss()
            val todoEdit = mDialogView.todoEditText.text.toString()
            users[position] = User(todoEdit)
            notifyItemChanged(position)

            dbHelper.updateLog(position, todoEdit)
        }

        mDialogView.dialogCancelBtn.setOnClickListener {
            mAlertDialog.dismiss()
        }
    }

    private fun alertDelete(holder: MyViewHolder, position: Int) {
        mDialogView = LayoutInflater.from(holder.itemView.context).inflate(R.layout.dialog_delete_todo, null)
        val mBuilder = AlertDialog.Builder(holder.itemView.context)
            .setView(mDialogView)
            .setTitle(R.string.delete)

        val  mAlertDialog = mBuilder.show()
        mDialogView.dialogDelete.setText(R.string.btnDelete)

        mDialogView.dialogDelete.setOnClickListener {
            mAlertDialog.dismiss()
            users.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, this.itemCount)
        }

        mDialogView.dialogCancelDelete.setOnClickListener {
            mAlertDialog.dismiss()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val viewHolder = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return MyViewHolder(viewHolder)
    }

}
