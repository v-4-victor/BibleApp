package com.v4vic.bibleapp.workmanager

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.v4vic.bibleapp.notification.NotificationHelper
import java.util.concurrent.TimeUnit

class BibleReminderWorker(
    appContext: Context,
    params: WorkerParameters
) : Worker(appContext, params) {

    override fun doWork(): Result {
        NotificationHelper.showNotification(
            context = applicationContext,
            title = "Напоминание 📖",
            message = "Самое время прочитать главу из Библии"
        )
        return Result.success()
    }
}

fun scheduleBibleReminder(context: Context) {
    val request = PeriodicWorkRequestBuilder<BibleReminderWorker>(
        3, TimeUnit.HOURS
    ).build()

    WorkManager.getInstance(context).enqueueUniquePeriodicWork(
        "bible_reminder",
        ExistingPeriodicWorkPolicy.KEEP,
        request
    )
}