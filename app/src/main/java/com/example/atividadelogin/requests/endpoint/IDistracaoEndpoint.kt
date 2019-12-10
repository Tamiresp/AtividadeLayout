package com.example.atividadelogin.requests.endpoint

import com.example.atividadelogin.requests.entity_request.DistracaoResponse
import retrofit2.Call
import retrofit2.http.GET

interface IDistracaoEndpoint {
    @GET("everything?q=bitcoin&apiKey=787c71f301b74b4da4709935a3df4c8f")
    fun getDistracoes(): Call<DistracaoResponse>
}