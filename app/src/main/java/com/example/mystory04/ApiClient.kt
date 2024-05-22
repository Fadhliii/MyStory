package com.example.mystory04

import com.example.mystory04.Response.ApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiConfig {
    fun getApiService(): ApiService {
        val loggingInterceptor =
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://story-api.dicoding.dev/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        return retrofit.create(ApiService::class.java)
    }
}
//object ApiClient {
//    private val okHttpClient = OkHttpClient.Builder()
//
//        .apply {
//            val loggingInterceptor = HttpLoggingInterceptor()
//            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
//            addInterceptor(loggingInterceptor)
//            Log.d("ApiClient", "okHttpClient: $this")
//        }
//
//        .readTimeout(25, TimeUnit.SECONDS)      // 25 seconds to read
//        .writeTimeout(300, TimeUnit.SECONDS)   // 300 seconds to write
//        .connectTimeout(55, TimeUnit.SECONDS) // 55 seconds to connect
//        .build()
//
//    val retrofit = Retrofit.Builder()
//        .baseUrl("https://story-api.dicoding.dev/v1/")
//        .addConverterFactory(GsonConverterFactory.create())
//        .client(okHttpClient)   // set the OkHttpClient
//        .build() // create the Retrofit instance
//
//    init {
//        Log.d("ApiClient", "retrofit: $retrofit")
//    }
//    val getApiService = retrofit.create<ApiService>() // create an instance of the ApiService
//}