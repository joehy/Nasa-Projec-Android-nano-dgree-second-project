/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.udacity.asteroidradar.database

import android.util.Log
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.udacity.asteroidradar.model.Asteroid


@Entity
data class DatabaseEntities constructor(
        @PrimaryKey
        val id: Long, val codename: String, val closeApproachDate: String,
        val absoluteMagnitude: Double, val estimatedDiameter: Double,
        val relativeVelocity: Double, val distanceFromEarth: Double,
        val isPotentiallyHazardous: Boolean)

fun List<DatabaseEntities>.asDomainModel(): List<Asteroid> {
    return map {
        Asteroid(
            id =it.id ,
            codename =it.codename,
            closeApproachDate =it.closeApproachDate,
            absoluteMagnitude =it.absoluteMagnitude,
            estimatedDiameter =it.estimatedDiameter,
            relativeVelocity =it.relativeVelocity,
            distanceFromEarth =it.distanceFromEarth,
            isPotentiallyHazardous=it.isPotentiallyHazardous
        )
    }
}
