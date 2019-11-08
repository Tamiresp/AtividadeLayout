package com.example.atividadelogin.activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.atividadelogin.R
import com.example.atividadelogin.adapters.MyAdapter
import com.example.atividadelogin.utils.User
import kotlinx.android.synthetic.main.activity_list.*
import kotlinx.android.synthetic.main.dialog_new_todo.view.*

class ListActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private  lateinit var adapter: MyAdapter
    private val users = ArrayList<User>()
    private lateinit var mDialogView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        val btnAdd = btnAdd

        val txtLogin = textView

        txtLogin.text = getString(R.string.bemVindo) + " " + intent.getStringExtra("login")

        supportActionBar?.title = getString(R.string.titlePage)

        recyclerView = recycler

        recyclerView.layoutManager = LinearLayoutManager(this)

        btnAdd.setOnClickListener {
            showCreateTaskDialog()
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
    }

    private fun showCreateTaskDialog() {
        mDialogView = LayoutInflater.from(this).inflate(R.layout.dialog_new_todo, null)
        val mBuilder = AlertDialog.Builder(this)
            .setView(mDialogView)
            .setTitle(R.string.txtTodo)

        val  mAlertDialog = mBuilder.show()

        mDialogView.dialogAddBtn.setOnClickListener {
            mAlertDialog.dismiss()
            val todoEdit = mDialogView.todoEditText.text.toString()
            adapter = MyAdapter(users)
            //adapter = MyAdapter(mutableListOf<User>())
            recyclerView.adapter = adapter
            adapter.addTask(User(todoEdit))
        }

        mDialogView.dialogCancelBtn.setOnClickListener {
            mAlertDialog.dismiss()
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return true
    }

    fun alertDelete(){
        val builder = AlertDialog.Builder(this)
        builder.setCancelable(false)
        builder.setMessage(R.string.delete)
        builder.setPositiveButton(R.string.yes) { dialog, _ ->
            val dialog = builder.create()
            dialog.show()
            }
            builder.setNegativeButton(R.string.no) { dialog, _ ->
                dialog.dismiss()
            }
    }


}
