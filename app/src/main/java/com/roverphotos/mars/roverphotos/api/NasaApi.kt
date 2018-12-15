package com.roverphotos.mars.roverphotos.api

import android.util.Log
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.roverphotos.mars.roverphotos.constant.AppConstants
import com.roverphotos.mars.roverphotos.data.GetMarsPhotos
import com.roverphotos.mars.roverphotos.data.GetRoverResponse
import kotlinx.coroutines.Deferred
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

/**
 *  Created by metecanduyal on 15.12.2018.
 */

interface NasaApi {

    @GET("mars-photos/api/v1/rovers/")
    fun getRovers(@Query("api_key") apiKey: String = AppConstants.API_KEY): Deferred<GetRoverResponse>

    @GET("mars-photos/api/v1/rovers/{rover}/photos")
    fun getPhotos(
        @Path("rover") roverName: String,
        @Query("earth_date") earthDate: String,
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = AppConstants.API_KEY
    ): Deferred<GetMarsPhotos>

    companion object {
        fun create(): NasaApi = create(HttpUrl.parse(AppConstants.BASE_API_URL)!!)

        fun create(httpUrl: HttpUrl): NasaApi {
            val logger = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger {
                Log.d("API", it)
            })

            logger.level = HttpLoggingInterceptor.Level.BASIC

            val client = OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .addInterceptor(logger)
                .build()
            val gson = GsonBuilder()
                .setLenient()
                .create()

            return Retrofit.Builder()
                .baseUrl(httpUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .build()
                .create(NasaApi::class.java)
        }
    }
}