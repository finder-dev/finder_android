package com.android.finder.network.response

import com.google.gson.annotations.SerializedName
import java.util.*

data class EmailSignUpResponse(
    val success : Boolean,
    val response : SuccessSignUpResponse?,
    val errorResponse: ErrorResponse
)


