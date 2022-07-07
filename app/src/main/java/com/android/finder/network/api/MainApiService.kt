package com.android.finder.network.api

import com.android.finder.network.response.SuccessProfileResponse
import retrofit2.Call
import retrofit2.http.GET

interface MainApiService {

    @GET("api/users")
    fun getUserProfile() : Call<SuccessProfileResponse>
}