package com.udacity.asteroidradar.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.udacity.asteroidradar.database.AsteroidDataBase
import com.udacity.asteroidradar.database.AsteroidRepository
import com.udacity.asteroidradar.database.getDataBase
import retrofit2.HttpException

class RefreshDataWorker(context: Context,params:WorkerParameters)
    :CoroutineWorker(context,params) {
    override suspend fun doWork(): Result {
        val database = getDataBase(applicationContext)
        val repository = AsteroidRepository(database)
       return try {
            repository.refreshAsteroid()
           Result.success()
        }catch (e:HttpException){
           Result.retry()
        }

    }
}