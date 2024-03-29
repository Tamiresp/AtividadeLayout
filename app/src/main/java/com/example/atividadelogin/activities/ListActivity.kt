package com.example.atividadelogin.activities

import android.annotation.SuppressLint
import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.atividadelogin.R
import com.example.atividadelogin.adapters.MyAdapter
import com.example.atividadelogin.data.DatabaseLogin
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
    private val dbHelperLogin = DatabaseLogin(this)
    lateinit var login: String
    private val TAG = ListActivity::class.java.simpleName
    private var id: Int = 0

    @SuppressLint("RestrictedApi")
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        val btnAdd = btnAdd

        val txtLogin = textView

        login = intent.getStringExtra("login")

        txtLogin.text = getString(R.string.bemVindo) + " " + login

        recyclerView = recycler

        recyclerView.layoutManager = LinearLayoutManager(this)

        btnAdd.setOnClickListener {
            showCreateTaskDialog()
        }

        adapter = MyAdapter(users)
        recyclerView.adapter = adapter

        val logins = mutableListOf<String>()
        with(dbHelperLogin.getLogs()) {
            while (moveToNext()) {
                val itemLogin = getString(getColumnIndexOrThrow(Contract.LoginEntry.COLUMN_NAME_LOGIN))
                logins.add(itemLogin)
                if (logins.contains(login)){
                    with(dbHelperLogin.getLog(login)) {
                        while (moveToNext()) {
                            id = getInt(getColumnIndexOrThrow(Contract.LoginEntry.COLUMN_NAME_ID))
                            with(dbHelper.getLog(id)) {
                                while (moveToNext()) {
                                    val itemTodo = getString(getColumnIndexOrThrow(Contract.TaskEntry.COLUMN_NAME_TODO))
                                    val day = getString(getColumnIndexOrThrow(Contract.TaskEntry.COLUMN_DATE))

                                    adapter.addTask(Task(itemTodo, day))
                                }
                            }
                        }
                    }
                }
            }
        }

        myScheduler()

        supportActionBar?.title = getString(R.string.titlePage)
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

            dbHelper.insertLog(todoEdit, date, id)
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
            R.id.action_distracao -> {
                val intent = Intent(this, DistracaoActivity::class.java)
                startActivity(intent)
            }
            R.id.action_maps -> {
                val intentMap = Intent(this, MapsActivity::class.java)
                startActivity(intentMap)
            }
        }
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        dbHelper.close()
    }
}