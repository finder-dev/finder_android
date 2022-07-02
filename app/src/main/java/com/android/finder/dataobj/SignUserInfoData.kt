package com.android.finder.dataobj

data class SignUserInfoData(
    val email : String,
    val emailAuthCode : String,
    val password : String,
    val confirmPassword : String,
    val MBTI : String,
    val nickname : String
)
