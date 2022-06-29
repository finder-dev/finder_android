package com.android.finder.network.response

data class ErrorResponse(
    val status : Int,
    val errorMessages : List<String>
)