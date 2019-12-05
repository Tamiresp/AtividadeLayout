package com.example.atividadelogin.activities

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.work.*
import com.example.atividadelogin.R
import com.example.atividadelogin.adapters.DistracaoAdapter
import com.example.atividadelogin.service.DownloadWorker
import kotlinx.android.synthetic.main.activity_distracao.*
import kotlinx.android.synthetic.main.item_distracao.*

class DistracaoActivity : AppCompatActivity() {
    private  lateinit var adapter: DistracaoAdapter
    private val items = ArrayList<String>()
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_distracao)

        supportActionBar?.title = getString(R.string.distracao_title)

        recyclerView = recycler_distracao

        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = DistracaoAdapter(items)
        recyclerView.adapter = adapter

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return true
    }

    override fun onResume() {
        super.onResume()
       // button2.setOnClickListener {
            val workManager = WorkManager.getInstance()
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED).build()
            val inputData = Data.Builder()
                .putString("url", "https://newsapi.org/v2/everything?q=bitcoin&apiKey=787c71f301b74b4da4709935a3df4c8f").build()
            val oneTimeWorkRequest = OneTimeWorkRequest.Builder(DownloadWorker::class.java)
                .setConstraints(constraints)
                .setInputData(inputData).build()

            workManager.enqueue(oneTimeWorkRequest)

            workManager.getWorkInfoByIdLiveData(oneTimeWorkRequest.id)
                .observe(this, Observer { workInfo ->
                    if (workInfo != null && workInfo.state.isFinished){
                        item.text = workInfo.outputData.getString("json")
                    }
                })
       // }
    }
}
