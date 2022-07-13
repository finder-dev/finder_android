package com.android.finder.dataobj

data class CommunityDetailDto(
    val answerCount: Int,
    val answerHistDtos: List<Any>,
    val communityContent: String,
    val communityId: Int,
    val communityImgDtos: List<CommunityImgDto>,
    val communityMBTI: String,
    val communityTitle: String,
    val createTime: String,
    val isQuestion: Boolean,
    var likeCount: Int,
    var likeUser: Boolean,
    val saveUser: Boolean,
    val userId: Int,
    val userMBTI: String,
    val userNickname: String
)