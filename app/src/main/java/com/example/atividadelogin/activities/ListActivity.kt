package com.example.atividadelogin.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.atividadelogin.adapters.MyAdapter
import com.example.atividadelogin.R
import com.example.atividadelogin.utils.User
import kotlinx.android.synthetic.main.activity_list.*

class ListActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private  lateinit var adapter: MyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        recyclerView = recycler

        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = MyAdapter(mutableListOf<User>())
    }
}
