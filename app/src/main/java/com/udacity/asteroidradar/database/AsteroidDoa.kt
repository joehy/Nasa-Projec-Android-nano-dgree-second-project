package com.udacity.asteroidradar.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.udacity.asteroidradar.model.Asteroid
import kotlinx.coroutines.flow.Flow

@Dao
interface AsteroidDoa {
 @Query("select * from databaseEntities")
 fun getAllAsteroids():LiveData<List<DatabaseEntities>>
 @Insert(onConflict=OnConflictStrategy.REPLACE)
 fun insertAll(vararg databaseEntities: DatabaseEntities)
 @Query("DELETE FROM databaseEntities WHERE closeApproachDate < :today")
 fun deletePreviousDayAsteroids(today: String): Int
 @Query("SELECT * FROM databaseEntities WHERE closeApproachDate >= :startDate AND closeApproachDate <= :endDate ORDER BY closeApproachDate ASC")
 fun filterByDate(startDate: String, endDate: String): LiveData<List<DatabaseEntities>>
}