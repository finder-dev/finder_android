package com.android.finder

import com.android.finder.dataobj.CommentData
import com.android.finder.dataobj.Content
import com.android.finder.dataobj.ReCommentData

data class LikeCommunityContent(val content : Content, val position: Int)

data class MoveToCommunityDetail(val communityId: Long)

data class MoveToDebateDetail(val debateId: Long)

data class ImageDeleteEvent(val imageUrl: String)

data class CommentAttributeClickEvent(val data : CommentData)

data class ReCommentAttributeClickEvent(val data : ReCommentData)