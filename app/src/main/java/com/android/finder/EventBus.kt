package com.android.finder

import com.android.finder.dataobj.CommentData
import com.android.finder.dataobj.Content

data class LikeCommunityContent(val content : Content)

data class MoveToCommunityDetail(val communityId: Long)

data class ImageDeleteEvent(val imageUrl: String)

data class CommentAttributeClickEvent(val data : CommentData)