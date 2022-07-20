package com.finder.android.mbti

import com.finder.android.mbti.dataobj.CommentData
import com.finder.android.mbti.dataobj.Content
import com.finder.android.mbti.dataobj.ReCommentData

data class LikeCommunityContent(val content : Content, val position: Int)

data class MoveToCommunityDetail(val communityId: Long)

data class MoveToDebateDetail(val debateId: Long)

data class ImageDeleteEvent(val imageUrl: String)

data class CommentAttributeClickEvent(val data : CommentData)

data class ReCommentAttributeClickEvent(val data : ReCommentData)