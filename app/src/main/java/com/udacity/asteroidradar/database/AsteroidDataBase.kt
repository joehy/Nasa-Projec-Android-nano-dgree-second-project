package com.udacity.asteroidradar.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [DatabaseEntities::class], version = 1)
abstract class AsteroidDataBase:RoomDatabase(){
    abstract val asteroidDoa:AsteroidDoa
}

private lateinit var INSTANCE:AsteroidDataBase

fun getDataBase(context: Context):AsteroidDataBase {
    synchronized(AsteroidDataBase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                AsteroidDataBase::class.java,
                "asteriod"
            ).build()

        }
    }
    return INSTANCE
}