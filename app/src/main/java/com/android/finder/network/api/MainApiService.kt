package com.android.finder.network.api

import com.android.finder.dataobj.CommunityHotTitleData
import com.android.finder.network.response.CommunityDetailResponse
import com.android.finder.network.response.CommunityHotResponse
import com.android.finder.network.response.CommunityListResponse
import com.android.finder.network.response.SuccessProfileResponse
import com.google.gson.JsonObject
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

    @Multipart
    @PATCH("api/community/{communityId}")
    fun modifyCommunityContents(
        @Path("communityId") communityId: Long,
        @PartMap params: HashMap<String, RequestBody>,
        @Part deleteImages: List<MultipartBody.Part>,
        @Part addImages: List<MultipartBody.Part>
    ) : Call<Unit>

    @GET("api/community")
    fun getCommunityPostList(
        @Query("mbti") mbti: String? = null,
        @Query("orderBy") orderBy: String? = null, //ANSWER_COUNT, CREATE_TIME →default: CREATE_TIME
        @Query("page") page: Int
    ) : Call<CommunityListResponse>

    @GET("api/community/hot")
    fun getCommunityHotList() : Call<CommunityHotResponse>

    @POST("api/community/{communityId}/like")
    fun likeChange(
        @Path("communityId") communityId: Long
    ) : Call<Unit>

    @GET("api/community/{communityId}")
    fun getCommunityDetail(
        @Path("communityId") communityId: Long
    ) : Call<CommunityDetailResponse>

    @POST("/api/community/{communityId}/save")
    fun saveChange(
        @Path("communityId") communityId : Long
    ) : Call<Unit>

    @FormUrlEncoded
    @POST("/api/community/{communityId}/answers")
    fun createAnswer(
        @Path("communityId") communityId : Long,
        @Field("content") content : String
    ) : Call<Unit>

    @DELETE("api/community/{communityId}")
    fun deleteCommunityContent(
        @Path("communityId") communityId : Long
    ) : Call<Unit>

    @POST("api/community/{communityId}/report")
    fun reportCommunityContent(
        @Path("communityId") communityId : Long
    ) : Call<Unit>

    @DELETE("api/community/answers/{answerId}")
    fun deleteCommentAndReComment(
        @Path("answerId") answerId : Long
    ) : Call<Unit>

    @POST("/api/community/answers/{answerId}/report")
    fun reportCommentAndReComment(
        @Path("answerId") answerId : Long
    ) : Call<Unit>
}