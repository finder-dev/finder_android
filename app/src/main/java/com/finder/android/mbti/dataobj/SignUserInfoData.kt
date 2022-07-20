package com.finder.android.mbti.dataobj

data class SignUserInfoData(
    val email : String,
    val emailAuthCode : String,
    val password : String,
    val confirmPassword : String,
    val MBTI : String,
    val nickname : String
)
