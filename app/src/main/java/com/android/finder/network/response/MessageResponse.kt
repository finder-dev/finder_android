package com.android.finder.network.response

data class MessageResponse(
    val success : Boolean,
    val response : SuccessMessageResponse?,
    val errorResponse: ErrorResponse
)
