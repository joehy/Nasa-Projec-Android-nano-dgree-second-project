package com.udacity.asteroidradar.api

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.Constants.ApiKey
import com.udacity.asteroidradar.Constants.NASA_URL
import com.udacity.asteroidradar.model.PictureOfDay
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


interface AsteroidService{
    @GET("neo/rest/v1/feed")
    fun getAllAsteroidInfoAsync(@Query("start_date")startDate:String,
                                @Query("end_date")endDate:String,
                                @Query("api_key")apiKey:String):Deferred<ResponseBody>
    @GET("planetary/apod")
    fun getPictureOfDayAsync(@Query("api_key")apiKey:String):Deferred<PictureOfDay>
}

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

object Network {
    // Configure retrofit to parse JSON and use coroutines
    private val retrofit = Retrofit.Builder()
        .baseUrl(NASA_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()

    val service: AsteroidService  = retrofit.create(AsteroidService::class.java)
}

suspend fun getDayPic():PictureOfDay?{
    var pictureOfDay: PictureOfDay
    withContext(Dispatchers.IO){
        pictureOfDay=Network.service.getPictureOfDayAsync(ApiKey).await()
    }
    if (pictureOfDay.mediaType == "image") {
        return pictureOfDay
    }
    return null
}
