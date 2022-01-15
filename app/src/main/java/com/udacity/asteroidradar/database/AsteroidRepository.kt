package com.udacity.asteroidradar.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.udacity.asteroidradar.api.Network
import com.udacity.asteroidradar.Constants.ApiKey
import com.udacity.asteroidradar.api.getCurrentDate
import com.udacity.asteroidradar.api.getDatePlus7
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.model.Asteroid
import com.udacity.asteroidradar.model.asDataBasModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

class AsteroidRepository (private val asteroidDataBase:AsteroidDataBase){


    suspend fun refreshAsteroid(){
        withContext(Dispatchers.IO) {
            Network.service.getAllAsteroidInfoAsync(getCurrentDate(), getDatePlus7(), ApiKey)
                .await().let {
                    val asteroidList = parseAsteroidsJsonResult(JSONObject(it.string()))
                    asteroidDataBase.asteroidDoa.insertAll(*asteroidList.asDataBasModel())
                }
        }

    }
    suspend fun deletePreviousDayAsteroids(){
        withContext(Dispatchers.IO){
            asteroidDataBase.asteroidDoa.deletePreviousDayAsteroids(getCurrentDate())
        }
    }
}