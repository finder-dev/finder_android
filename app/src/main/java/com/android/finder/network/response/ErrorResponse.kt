package com.android.finder.network.response

data class ErrorResponse(
    val errorMessages: List<String>,
    val status: Int
)