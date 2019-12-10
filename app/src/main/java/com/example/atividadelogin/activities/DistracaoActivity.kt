package com.example.atividadelogin.activities

import ApiDistracao
import android.app.ProgressDialog
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.atividadelogin.R
import com.example.atividadelogin.adapters.DistracaoAdapter
import com.example.atividadelogin.requests.endpoint.IDistracaoEndpoint
import com.example.atividadelogin.requests.entity_request.ArticlesRequest
import com.example.atividadelogin.requests.entity_request.DistracaoResponse
import kotlinx.android.synthetic.main.activity_distracao.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DistracaoActivity : AppCompatActivity() {
    private  lateinit var adapter: DistracaoAdapter
    private val items = ArrayList<ArticlesRequest>()
    private lateinit var recyclerView: RecyclerView
    private lateinit var mDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        mDialog = ProgressDialog(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_distracao)

        supportActionBar?.title = getString(R.string.distracao_title)

        recyclerView = recycler_distracao

        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = DistracaoAdapter(items)
        recyclerView.adapter = adapter
        mDialog.setMessage("Retrieving data")
        mDialog.show()

        loadDistracoes()

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

    fun loadDistracoes(){
        val result = ApiDistracao.getInstance().create(IDistracaoEndpoint::class.java).getDistracoes()
        result.enqueue(object : Callback<DistracaoResponse> {

            override fun onFailure(call: Call<DistracaoResponse>, t: Throwable) {

            }

            override fun onResponse(
                call: Call<DistracaoResponse>,
                response: Response<DistracaoResponse>) {
                val result: DistracaoResponse = response.body()!!

                for (item in result.listOfArticles ) {
                    adapter.addItem(ArticlesRequest(item.title, item.author, item.description))
                }
                mDialog.dismiss()
            }
        })
    }
}
