package com.example.atividadelogin.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.work.*
import com.example.atividadelogin.R
import com.example.atividadelogin.adapters.MapAdapter
import com.example.atividadelogin.service.DownloadWorker
import com.example.atividadelogin.service.MyAsyncTask
import com.example.atividadelogin.service.TaskListener
import com.example.atividadelogin.utils.Task
import kotlinx.android.synthetic.main.activity_download.*
import java.net.URL

class DownloadActivity : AppCompatActivity() {
    private lateinit var asyncTask: MyAsyncTask
    private lateinit var recyclerView: RecyclerView
    private  lateinit var adapter: MapAdapter
    private val users = ArrayList<Task>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_download)
//
//        recyclerView = findViewById(R.id.recyclerView)
//
//        recyclerView.layoutManager = LinearLayoutManager(this)
//
//        adapter = MapAdapter(users)
//        recyclerView.adapter = adapter


        asyncTask = MyAsyncTask(this, object : TaskListener {
            override fun onTaskComplete(s: String) {
                textView2.text = s
            }
        })
    }

    override fun onResume() {
        super.onResume()
        button.setOnClickListener { asyncTask.execute(
            URL("https://newsapi.org/v2/everything?q=bitcoin&apiKey=API_KEY"))
        }

        button2.setOnClickListener {
            val workManager = WorkManager.getInstance()
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED).build()
            val inputData = Data.Builder()
                .putString("url", "http://www.omdbapi.com/?t=Game%20of%20Thrones&Season=1&apikey=59317c2e").build()
            val oneTimeWorkRequest = OneTimeWorkRequest.Builder(DownloadWorker::class.java)
                .setConstraints(constraints)
                .setInputData(inputData).build()

            workManager.enqueue(oneTimeWorkRequest)

            workManager.getWorkInfoByIdLiveData(oneTimeWorkRequest.id)
                .observe(this, Observer { workInfo ->
                if (workInfo != null && workInfo.state.isFinished){
                    textView2.text = workInfo.outputData.getString("json")
                }
            })
        }
    }
}
