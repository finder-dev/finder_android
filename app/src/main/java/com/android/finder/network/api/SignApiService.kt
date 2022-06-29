package com.android.finder.network.api

import com.android.finder.network.response.SendCodeResponse
import com.android.finder.network.response.SuccessMessageResponse
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface SignApiService {

    @FormUrlEncoded
    @POST("/api/mail/send")
    fun getAuthCodeByEmail(
        @Field("email") email: String
    ) : Call<SendCodeResponse>
}