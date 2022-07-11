package com.android.finder.dataobj

import com.google.gson.annotations.SerializedName

data class CommunityListDto(
    @SerializedName("content") val contents: List<Content>,
    @SerializedName("pageable") val pageable: Pageable,
    @SerializedName("last") val last : Boolean,
    @SerializedName("size") val size : Int
)

data class Content(
    @SerializedName("communityId") val communityId: Long,
    @SerializedName("communityTitle") val communityTitle: String,
    @SerializedName("communityContent") val communityContent: String,
    @SerializedName("communityMBTI") val communityMBTI: String,
    @SerializedName("imageUrl") val imageUrl: String?,
    @SerializedName("userNickname") val userNickname: String,
    @SerializedName("userMBTI") val userMBTI: String,
    @SerializedName("likeUser") var likeUser: Boolean,
    @SerializedName("likeCount") var likeCount: Int,
    @SerializedName("answerCount") val answerCount: Int,
    @SerializedName("isQuestion") val isQuestion: Boolean,
    @SerializedName("createTime") val createTime: String
)

data class Pageable(
    @SerializedName("offset") val offset: Int,
    @SerializedName("pageSize") val pageSize: Int,
    @SerializedName("pageNumber") val pageNumber: Int,
    @SerializedName("paged") val paged: Boolean,
    @SerializedName("unpaged") val unPaged: Boolean
)