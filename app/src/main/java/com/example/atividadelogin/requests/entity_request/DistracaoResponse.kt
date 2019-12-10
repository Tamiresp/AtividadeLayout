package com.example.atividadelogin.requests.entity_request

import com.google.gson.annotations.SerializedName


data class DistracaoResponse(
    @SerializedName("articles")
    val listOfArticles: List<ArticlesRequest>){

}