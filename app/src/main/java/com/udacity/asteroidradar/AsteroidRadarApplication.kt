package com.udacity.asteroidradar

import android.app.Application
import android.os.Build
import androidx.work.*
import com.udacity.asteroidradar.work.RefreshDataWorker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Dispatcher
import java.util.concurrent.TimeUnit

class AsteroidRadarApplication: Application() {
    val applicationScope= CoroutineScope(Dispatchers.Default)
    override fun onCreate() {
        super.onCreate()
        runScope()
    }

    private fun runScope() {
        applicationScope.launch {
            setupAppWorker()
        }
    }

    private fun setupAppWorker() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.UNMETERED)
            .setRequiresBatteryNotLow(true)
            .setRequiresCharging(true)
            .apply {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    setRequiresDeviceIdle(true)
                }
            }.build()
        val repeatingRequest= PeriodicWorkRequestBuilder<RefreshDataWorker>(
            1,TimeUnit.DAYS)
            .setConstraints(constraints)
            .build()
        WorkManager.getInstance().enqueueUniquePeriodicWork(
            "RefreshDataWorker",
            ExistingPeriodicWorkPolicy.KEEP,
            repeatingRequest
        )


    }

}