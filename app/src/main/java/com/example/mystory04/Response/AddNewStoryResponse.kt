package com.example.mystory04.Response

import com.google.gson.annotations.SerializedName
// Guest only
data class AddNewStoryResponse(

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)
