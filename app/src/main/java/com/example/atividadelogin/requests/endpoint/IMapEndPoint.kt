package com.example.atividadelogin.requests.endpoint

import com.example.atividadelogin.requests.entity_request.MapRequest
import retrofit2.Call
import retrofit2.http.GET

interface IMapEndPoint {
    @GET("action/datastore_search?resource_id=78fccbb7-b44d-49a8-8c82-bcc1dc8463b4&limit=20")
    fun getPontos(): Call<MapRequest>
}