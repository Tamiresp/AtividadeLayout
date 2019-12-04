package com.example.atividadelogin.service

import android.app.job.JobParameters
import android.app.job.JobService
import android.util.Log

class MyJobService : JobService() {
    private val TAG = MyJobService::class.java.simpleName
    private var isWorking = false
    override fun onStopJob(params: JobParameters?): Boolean {
        return false
    }

    override fun onStartJob(params: JobParameters?): Boolean {
        Log.d(TAG, "Job started")
        isWorking = true
        startWorkOnNewThread(params)
        return isWorking
    }

    private fun startWorkOnNewThread(params: JobParameters?) {
        Thread(Runnable {
            doWork(params)
        }).start()
    }

    private fun doWork(params: JobParameters?) {
        for (i in 0..1000){
            try {
                Thread.sleep(10)
            } catch (e: Exception){

            }
            Log.d(TAG, "Job finished")
            isWorking = false
            val needReschedule = false
            jobFinished(params, needReschedule)
        }
    }

}