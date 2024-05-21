package com.example.mystory04

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit

object ApiClient {
    private val okHttpClient = OkHttpClient.Builder()
        .apply {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            addInterceptor(loggingInterceptor)

        }
        .readTimeout(25, TimeUnit.SECONDS)      // 25 seconds to read
        .writeTimeout(300, TimeUnit.SECONDS)   // 300 seconds to write
        .connectTimeout(55, TimeUnit.SECONDS) // 55 seconds to connect
        .build()

    val retrofit = Retrofit.Builder()
        .baseUrl("https://story-api.dicoding.dev")
        .client(okHttpClient)   // set the OkHttpClient
        .addConverterFactory(GsonConverterFactory.create()) // convert JSON to model
        .build() // create the Retrofit instance
    val apiService = retrofit.create<ApiService>() // create an instance of the ApiService
}