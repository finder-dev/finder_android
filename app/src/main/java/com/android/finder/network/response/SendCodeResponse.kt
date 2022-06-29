package com.android.finder.network.response

data class SendCodeResponse(
    val success : Boolean,
    val response : SuccessMessageResponse,
    val errorResponse: ErrorResponse
)
