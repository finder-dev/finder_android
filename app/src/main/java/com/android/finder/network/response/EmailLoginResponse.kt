package com.android.finder.network.response

data class EmailLoginResponse(
    val success : Boolean,
    val response : SuccessLoginResponse?,
    val errorResponse: ErrorResponse
)


