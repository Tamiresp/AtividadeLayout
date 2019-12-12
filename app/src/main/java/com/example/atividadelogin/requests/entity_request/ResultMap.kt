package com.example.atividadelogin.requests.entity_request

import com.google.gson.annotations.SerializedName

class ResultMap(@SerializedName("records") val listRecordsMap: List<RecordsMap>) {
}