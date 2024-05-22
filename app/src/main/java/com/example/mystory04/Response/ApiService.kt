package com.example.mystory04.Response

import com.example.mystory04.Response.AddNewStoryResponse
import com.example.mystory04.Response.AddStoryLoginResponse
import com.example.mystory04.Response.DetailStoryResponse
import com.example.mystory04.Response.GetAllStoryResponse
import com.example.mystory04.Response.LoginResponse
import com.example.mystory04.Response.RegisterResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
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

//
}