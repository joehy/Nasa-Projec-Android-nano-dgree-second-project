package com.udacity.asteroidradar.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.udacity.asteroidradar.api.getCurrentDate
import com.udacity.asteroidradar.api.getDatePlus7
import com.udacity.asteroidradar.api.getDayPic
import com.udacity.asteroidradar.model.PictureOfDay
import com.udacity.asteroidradar.database.AsteroidRepository
import com.udacity.asteroidradar.database.asDomainModel
import com.udacity.asteroidradar.database.getDataBase
import com.udacity.asteroidradar.model.Asteroid
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val database= getDataBase(application)
    private val repository=AsteroidRepository(database)

    private var _asteroids = MutableLiveData<List<Asteroid>>()
    val asteroids: LiveData<List<Asteroid>>
        get() = _asteroids

    private val _pictureOfDay = MutableLiveData<PictureOfDay>()
    val pictureOfDay: LiveData<PictureOfDay>
        get() = _pictureOfDay

    private suspend fun refreshDayPic(){
        _pictureOfDay.value= getDayPic()
    }

    init {
        onWeekFilterCheck()
        viewModelScope.launch {
            refreshDayPic()
            repository.refreshAsteroid()
        }
    }
    fun onWeekFilterCheck() {
        Log.i("el7ala","onWeekFilterCheck")
        viewModelScope.launch {
            database.asteroidDoa.filterByDate(getCurrentDate(), getDatePlus7())
                .collect {
                    _asteroids.value=it
                }
        }
    }

    fun onTodayFilterCheck() {
        Log.i("el7ala","onTodayFilterCheck")
        viewModelScope.launch {
            database.asteroidDoa.filterByDate(getCurrentDate(), getCurrentDate())
                .collect {
                    _asteroids.value=it
                }
        }
    }

    fun onSavedFilterCheck() {
        Log.i("el7ala","onSavedFilterCheck")
        viewModelScope.launch {
            database.asteroidDoa.getAllAsteroids()
                .collect {
                    _asteroids.value=it
                }
        }

     }


}