package com.example.mystory04

import android.credentials.CredentialDescription
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
    // POST request to add a
    @Multipart
    @POST("/stories/guest")
    suspend fun addNewStory(
            @Part file: MultipartBody.Part,
            @Part("description") description: RequestBody,
    ): AddNewStoryResponse

    @FormUrlEncoded
    @POST("/register")
    suspend fun registerUser(
            @Field("name") name: String,
            @Field("email") email: String,
            @Field("password") password: String
    ): RegisterResponse

    @FormUrlEncoded
    @POST("/login")
    suspend fun loginUser(
            @Field("email") email: String,
            @Field("password") password: String
    ): LoginResponse

    @Multipart
    @POST("/stories") // POST request to add a new story with authentication
    suspend fun addStoriesLogin(
            @Header("Authorization") authorization: String,
            @Part file: MultipartBody.Part,
            @Part("description") description: RequestBody,
    ): AddStoryLoginResponse

    @GET("/stories") // GET request to get all stories
    suspend fun getStories(
    ): GetAllStoryResponse

    @GET("/stories/:id")
    suspend fun getStory(
            @Header("Authorization") authorization: String
    ): DetailStoryResponse
}