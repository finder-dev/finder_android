package com.android.finder.network.api

import com.android.finder.network.response.SuccessProfileResponse
import com.google.gson.annotations.SerializedName
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface MainApiService {

    @GET("api/users")
    fun getUserProfile(): Call<SuccessProfileResponse>

    @Multipart
    @POST("api/community")
    fun writeCommunityContents(
        @PartMap params: HashMap<String, RequestBody>,
        @Part questionImgs: List<MultipartBody.Part>
    ) : Call<Unit>
}