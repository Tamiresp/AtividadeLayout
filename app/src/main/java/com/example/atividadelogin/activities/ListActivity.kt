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
import com.example.atividadelogin.data.DatabaseTask
import com.example.atividadelogin.utils.Contract
import com.example.atividadelogin.utils.Task
import kotlinx.android.synthetic.main.activity_list.*
import kotlinx.android.synthetic.main.dialog_new_todo.view.*

class ListActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private  lateinit var adapter: MyAdapter
    private val users = ArrayList<Task>()
    private lateinit var mDialogView: View
    private val dbHelper = DatabaseTask(this)
    lateinit var login: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        val btnAdd = btnAdd

        val txtLogin = textView

        login = intent.getStringExtra("login")

        txtLogin.text = getString(R.string.bemVindo) + " " + login

        supportActionBar?.title = getString(R.string.titlePage)

        recyclerView = recycler

        recyclerView.layoutManager = LinearLayoutManager(this)

        btnAdd.setOnClickListener {
            showCreateTaskDialog()
        }

        val itemsId = mutableListOf<String>()
        with(dbHelper.getLogs()) {
            while (moveToNext()) {
                val itemId = getString(getColumnIndexOrThrow(Contract.TaskEntry.COLUMN_NAME_TITLE))
                itemsId.add(itemId)
                adapter = MyAdapter(users)
                recyclerView.adapter = adapter
                adapter.addTask(Task(itemId))
            }
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
            recyclerView.adapter = adapter
            adapter.addTask(Task(todoEdit))

            dbHelper.insertLog(todoEdit)
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

    override fun onDestroy() {
        super.onDestroy()
        dbHelper.close()

    }

}
