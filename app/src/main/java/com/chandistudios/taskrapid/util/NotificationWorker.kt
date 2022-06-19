package com.chandistudios.taskrapid.util

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.chandistudios.taskrapid.ui.task.toTime

class NotificationWorker(
    context: Context,
    userParameters: WorkerParameters
) : Worker(context, userParameters) {

    val notificationTime = userParameters.inputData.getLong("notiTime", 0L)

    @RequiresApi(Build.VERSION_CODES.O)
    override fun doWork(): Result {
        if (notificationTime != 0L)
            return Result.success()
        else
            return Result.failure()
//        return try {
//            Result.success()
//        } catch (e: Exception) {
//            Result.failure()
//        }
    }
}