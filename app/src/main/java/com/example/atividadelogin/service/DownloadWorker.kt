package com.example.atividadelogin.service

import android.content.Context
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.io.BufferedInputStream
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Exception
import java.lang.StringBuilder
import java.net.HttpURLConnection
import java.net.URL

class DownloadWorker (context: Context, workerParams: WorkerParameters): Worker(context, workerParams) {
    private var urlConnection: HttpURLConnection? = null

    override fun doWork(): Result {
        val uri = inputData.getString("url")
        val url = URL(uri)
        val result = StringBuilder()

        try {
            urlConnection = url.openConnection() as HttpURLConnection
            urlConnection?.doInput = true
            urlConnection?.connectTimeout = 20 * 1000
            urlConnection?.readTimeout = 20 * 1000

            if (urlConnection?.responseCode == HttpURLConnection.HTTP_OK ){
                val buffer = BufferedInputStream(urlConnection?.inputStream)
                val reader = BufferedReader(InputStreamReader(buffer))

                var line: String?

                do {
                    line = reader.readLine()
                    if (line == null){
                        break
                    }
                    result.append(line)
                } while (true)
            }

        } catch (e: Exception){
            e.printStackTrace()
        } finally {
            urlConnection?.disconnect()
        }
        val outputData = Data.Builder().putString("json", result.toString()).build()
        return  Result.success(outputData)
    }
}