package com.example.atividadelogin.activities

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.atividadelogin.R
import com.example.atividadelogin.adapters.MyAdapter
import com.example.atividadelogin.data.DatabaseTask
import com.example.atividadelogin.service.MyJobService
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
    private val TAG = ListActivity::class.java.simpleName

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
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
        val daysWeek = mutableListOf<String>()
        with(dbHelper.getLogs()) {
            while (moveToNext()) {
                val itemId = getString(getColumnIndexOrThrow(Contract.TaskEntry.COLUMN_NAME_TODO))
                val day = getString(getColumnIndexOrThrow(Contract.TaskEntry.COLUMN_DATE))
                itemsId.add(itemId)
                daysWeek.add(day)
                adapter = MyAdapter(users)
                recyclerView.adapter = adapter
                adapter.addTask(Task(itemId, day))
            }
        }

//        val itemsId = mutableListOf<Int>()
//        val itemsTodo = mutableListOf<String>()
//        with(dbHelper.getLogs()) {
//            while (moveToNext()) {
//                val itemId = getInt(getColumnIndexOrThrow(Contract.TaskEntry.ID_USER))
//                itemsId.add(itemId)
//
//                with(dbHelper.getLog(itemsId[position])) {
//                    while (moveToNext()) {
//                        val itemTodo = getString(getColumnIndexOrThrow(Contract.TaskEntry.COLUMN_NAME_TODO))
//                        itemsTodo.add(itemTodo)
//                        adapter = MyAdapter(users)
//                        recyclerView.adapter = adapter
//                        adapter.addTask(Task(itemTodo))
//                    }
//                }
//            }
//        }

        myScheduler()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

    }

    @SuppressWarnings("ServiceCast")
    fun myScheduler(){
        val componentName = ComponentName(this, MyJobService::class.java)
        val jobInfo = JobInfo.Builder(12, componentName)
            .setRequiresCharging(true)
            .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
            .build()

        val jobScheduler = getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler?
        val resultCode = jobScheduler?.schedule(jobInfo)
        if (resultCode == JobScheduler.RESULT_SUCCESS){
            Log.d(TAG, "Job schedule")
        } else {
            Log.d(TAG, "Job not schedule")
        }
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
            val date = mDialogView.date_spinner.selectedItem.toString()

            adapter = MyAdapter(users)
            recyclerView.adapter = adapter
            adapter.addTask(Task(todoEdit, date))

            dbHelper.insertLog(todoEdit, date)
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
