package com.android.finder.network.response

data class EmailCodeCheckResponse(
    val success : Boolean,
    val response : SuccessMessageResponse?,
    val errorResponse: ErrorResponse
)