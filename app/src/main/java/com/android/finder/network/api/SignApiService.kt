package com.android.finder.network.api

import com.android.finder.network.response.EmailLoginResponse
import com.android.finder.network.response.MessageResponse
import retrofit2.Call
import retrofit2.http.*

interface SignApiService {

    @FormUrlEncoded
    @POST("/api/mail/send")
    fun getAuthCodeByEmail(
        @Field("email") email: String
    ) : Call<MessageResponse>

    @FormUrlEncoded
    @POST("api/mail/auth")
    fun checkAuthCodeByEmail(
        @Field("email") email : String,
        @Field("code") code : String
    ) : Call<MessageResponse>

    @GET("api/duplicated/nickname")
    fun checkDuplicateNickname(
        @Query("nickname") nickname: String
    ) : Call<MessageResponse>

    @FormUrlEncoded
    @POST("/api/signup")
    fun signUpByEmail(
        @Field("email") email : String,
        @Field("password") password : String,
        @Field("mbti") mbti : String,
        @Field("nickname") nickname : String
    ) : Call<EmailLoginResponse>

    @FormUrlEncoded
    @POST("api/login")
    fun login(
        @Field("email") email : String,
        @Field("password") password : String
    ) : Call<EmailLoginResponse>

}