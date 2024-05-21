package com.example.mystory04.Response

import com.google.gson.annotations.SerializedName
// User only
data class AddStoryLoginResponse(

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)
