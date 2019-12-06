package com.example.atividadelogin.activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.atividadelogin.R
import com.example.atividadelogin.adapters.DistracaoAdapter
import com.example.atividadelogin.service.MyAsyncTask
import com.example.atividadelogin.service.TaskListener
import kotlinx.android.synthetic.main.activity_distracao.*
import java.net.URL


class DistracaoActivity : AppCompatActivity() {
    private  lateinit var adapter: DistracaoAdapter
    private val items = ArrayList<String>()
    private lateinit var recyclerView: RecyclerView
    private lateinit var asyncTask: MyAsyncTask

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_distracao)

        val mDialogView = LayoutInflater.from(this).inflate(R.layout.item_distracao, null)
        val item2 = mDialogView.findViewById<TextView>(R.id.item)

        supportActionBar?.title = getString(R.string.distracao_title)

        recyclerView = recycler_distracao

        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = DistracaoAdapter(items)
        recyclerView.adapter = adapter

        asyncTask = MyAsyncTask(this, object : TaskListener {
            override fun onTaskComplete(s: String) {
                val json = getSharedPreferences("articles", 0)

                for (i in 0..s.length){
                    val quarta = json.getString("name", "")
                    item2.text = quarta
                }
                adapter.addItem(item2.text.toString())
            }
        })
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
        asyncTask.execute(
            URL("https://newsapi.org/v2/everything?q=bitcoin&apiKey=787c71f301b74b4da4709935a3df4c8f")
        )
    }
}
