package com.example.aksharadeepa

import android.app.Application
import androidx.work.*
import com.example.aksharadeepa.data.AppDatabase
import com.example.aksharadeepa.data.AppRepository
import com.example.aksharadeepa.reminder.ReminderWorker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.util.*
import java.util.concurrent.TimeUnit

class AksharaDeepaApp : Application() {
    val database by lazy { AppDatabase.getDatabase(this) }
    val repository by lazy { AppRepository(database.appDao()) }

    override fun onCreate() {
        super.onCreate()
        // Initialize data in a background scope if the database is empty
        CoroutineScope(SupervisorJob()).launch {
            repository.populateInitialData()
        }
        
        // Initial setup for default reminder (8 PM)
        scheduleDailyReminder(20, 0)
    }

    /**
     * Schedules a daily reminder at the specified hour and minute.
     * Replaces any existing reminder work.
     */
    fun scheduleDailyReminder(hour: Int = 20, minute: Int = 0) {
        val calendar = Calendar.getInstance()
        val now = Calendar.getInstance()
        
        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minute)
        calendar.set(Calendar.SECOND, 0)
        
        // If the time is already past for today, schedule it for tomorrow
        if (calendar.before(now)) {
            calendar.add(Calendar.DAY_OF_YEAR, 1)
        }
        
        val initialDelay = calendar.timeInMillis - now.timeInMillis

        val workRequest = PeriodicWorkRequestBuilder<ReminderWorker>(24, TimeUnit.HOURS)
            .setInitialDelay(initialDelay, TimeUnit.MILLISECONDS)
            .setConstraints(Constraints.Builder()
                .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
                .build())
            .addTag("daily_reminder")
            .build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "daily_reminder",
            ExistingPeriodicWorkPolicy.REPLACE,
            workRequest
        )
    }
}
