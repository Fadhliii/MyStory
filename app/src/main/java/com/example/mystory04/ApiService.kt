package com.example.mystory04

import retrofit2.http.POST

interface ApiService {
    @POST("/stories")
    suspend fun addNewStory(): AddNewStoryResponse

//    @Headers("Authorization: token <Personal Access Token>")
//    @GET("api/users")
//    fun getListUsers(@Query("page") page: String): Call<ResponseUser>
//
//    // Mendapatkan daftar pengguna berdasarkan id dengan path.
//    @GET("api/users/{id}")
//    fun getUser(@Path("id") id: String): Call<ResponseUser>
//
//    // Mengirim data pengguna menggunakan x-www-form-urlencoded.
//    @FormUrlEncoded
//    @POST("api/users")
//    fun createUser(
//            @Field("name") name: String,
//            @Field("job") job: String
//    ): Call<ResponseUser>
//
//    // Mengunggah berkas menggunakan Multipart
//    @Multipart
//    @PUT("api/uploadfile")
//    fun updateUser(
//            @Part("file") file: MultipartBody.Part,
//            @PartMap data: Map<String, RequestBody>
//    ): Call<ResponseUser>

}