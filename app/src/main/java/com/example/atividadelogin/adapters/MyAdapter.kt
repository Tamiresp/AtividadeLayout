package com.example.atividadelogin.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.atividadelogin.R
import com.example.atividadelogin.data.DatabaseTask
import com.example.atividadelogin.utils.Contract
import com.example.atividadelogin.utils.MyViewHolder
import com.example.atividadelogin.utils.Task
import kotlinx.android.synthetic.main.dialog_delete_todo.view.*
import kotlinx.android.synthetic.main.dialog_new_todo.view.*

class MyAdapter (private val users: MutableList<Task>): RecyclerView.Adapter<MyViewHolder>() {
    private lateinit var mDialogView: View

    override fun getItemCount(): Int {
        return users.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val user: Task = users[position]
        holder.title.text = user.todo
        holder.date.text = user.date
        deleteTask(holder, position)
        updateTask(holder, position)
    }

    private fun deleteTask(holder: MyViewHolder, position: Int){
        holder.btnDelete.setOnClickListener {
            alertDelete(holder, position)
        }
    }

    fun addTask(user: Task){
        users.add(user)
        notifyItemInserted(itemCount)
    }

    private fun updateTask(holder: MyViewHolder, position: Int){
        holder.btnEdit.setOnClickListener {
            showUpdateTaskDialog(holder, position)
        }
    }

    private fun showUpdateTaskDialog(holder: MyViewHolder, position: Int) {
        val dbHelper = DatabaseTask(holder.itemView.context)
        mDialogView = LayoutInflater.from(holder.itemView.context).inflate(R.layout.dialog_new_todo, null)
        val mBuilder = AlertDialog.Builder(holder.itemView.context)
            .setView(mDialogView)
            .setTitle(R.string.txtTodoupdate)

        val  mAlertDialog = mBuilder.show()
        val date = mDialogView.date_spinner.selectedItem.toString()

        mDialogView.dialogAddBtn.setOnClickListener {
            mAlertDialog.dismiss()
            val todoEdit = mDialogView.todoEditText.text.toString()
            users[position] = Task(todoEdit, date)
            notifyItemChanged(position)

            val itemsId = mutableListOf<Int>()

            with(dbHelper.getLogs()) {
                while (moveToNext()) {
                    val itemId = getInt(getColumnIndexOrThrow(Contract.TaskEntry.ID))
                    itemsId.add(itemId)
                }
            }

            dbHelper.updateLog(itemsId[position], todoEdit, date)
        }

        mDialogView.dialogCancelBtn.setOnClickListener {
            mAlertDialog.dismiss()
        }
    }

    private fun alertDelete(holder: MyViewHolder, position: Int) {
        val dbHelper = DatabaseTask(holder.itemView.context)
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
            notifyItemRangeChanged(position, itemCount)

            val itemsId = mutableListOf<Int>()

            with(dbHelper.getLogs()) {
                while (moveToNext()) {
                    val itemId = getInt(getColumnIndexOrThrow(Contract.TaskEntry.ID))
                    itemsId.add(itemId)
                }
            }
            dbHelper.removeLog(itemsId[position])
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
