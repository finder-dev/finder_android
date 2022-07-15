package com.android.finder.network.api

import com.android.finder.network.request.CreateDebateRequestDTO
import com.android.finder.network.request.ContentBodyRequestDTO
import com.android.finder.network.request.DebateOptionBodyRequestDTO
import com.android.finder.network.response.*
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

    @GET("api/debate")
    fun getDebateList(
        @Query("state") state : String = "PROCEEDING",
        @Query("page") page : Int
    ) : Call<DebateListResponseDTO>

    @GET("api/debate/hot")
    fun getDebateHot() : Call<DebateHotResponse>

    @POST("api/community/{communityId}/like")
    fun likeChange(
        @Path("communityId") communityId: Long
    ) : Call<Unit>

    @GET("api/community/{communityId}")
    fun getCommunityDetail(
        @Path("communityId") communityId: Long
    ) : Call<CommunityDetailResponse>

    @GET("api/debate/{debateId}")
    fun getDebateDetail(
        @Path("debateId") debateId : Long
    ) : Call<DebateDetailResponseDTO>

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

    @PATCH("/api/community/answers/{answerId}")
    fun modifyCommentAndReComment(
        @Path("answerId") answerId : Long,
        @Body content: ContentBodyRequestDTO
    ) : Call<Unit>

    @Headers("Content-Type: application/json")
    @PATCH("/api/debate/answers/{answerId}")
    fun modifyDebateCommentAndReComment(
        @Path("answerId") answerId : Long,
        @Body content: ContentBodyRequestDTO
    ) : Call<Unit>

    @FormUrlEncoded
    @POST("/api/community/answers/{answerId}/reply")
    fun createReComment(
        @Path("answerId") answerId : Long,
        @Field("content") content: String
    ) : Call<Unit>

    @Headers("Content-Type: application/json")
    @POST("/api/debate/answers/{answerId}/reply")
    fun createDebateReComment(
        @Path("answerId") answerId : Long,
        @Body content: ContentBodyRequestDTO
    ) : Call<Unit>

    @Headers("Content-Type: application/json")
    @POST("api/debate/{debateId}/answers")
    fun createDebateComment(
        @Path("debateId") debateId: Long,
        @Body content: ContentBodyRequestDTO
    ) : Call<Unit>

    @DELETE("api/community/answers/{answerId}")
    fun deleteCommentAndReComment(
        @Path("answerId") answerId : Long
    ) : Call<Unit>

    @DELETE("/api/debate/answers/{answerId}")
    fun deleteDebateCommentAndReComment(
        @Path("answerId") answerId : Long
    ) : Call<Unit>

    @POST("/api/community/answers/{answerId}/report")
    fun reportCommentAndReComment(
        @Path("answerId") answerId : Long
    ) : Call<Unit>

    @Headers("Content-Type: application/json")
    @POST("/api/debate/answers/{answerId}/report")
    fun reportDebateCommentAndReComment(
        @Path("answerId") answerId : Long
    ) : Call<Unit>

    @POST("api/debate/{debateId}/report")
    fun reportDebateContent(@Path("debateId") debateId: Long) : Call<Unit>

    @Headers("Content-Type: application/json")
    @POST("api/debate")
    fun createDebate(@Body data : CreateDebateRequestDTO) : Call<Unit>

    @Headers("Content-Type: application/json")
    @POST("api/debate/{debateId}")
    fun joinDebateSelect(
        @Path("debateId") debateId: Long,
        @Body option : DebateOptionBodyRequestDTO
    ) : Call<Unit>
}