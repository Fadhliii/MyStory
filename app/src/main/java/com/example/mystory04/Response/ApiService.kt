package com.example.mystory04.Response

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {
    // POST request to add
    @Multipart
    @POST("/stories/guest")
    suspend fun addNewStory(
            @Part file: MultipartBody.Part,
            @Part("description") description: RequestBody,
    ): AddNewStoryResponse

    @JvmSuppressWildcards
    @GET("/stories")
    suspend fun getAllStories(): GetAllStoryResponse


//
}