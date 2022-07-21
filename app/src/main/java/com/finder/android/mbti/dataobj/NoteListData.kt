package com.finder.android.mbti.dataobj

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class NoteListData(
    @SerializedName("userId") val targetUserId: Long,
    @SerializedName("userNickname") val userNickname : String,
    @SerializedName("content") val content: String,
    @SerializedName("createTime") val createTime : String
): Parcelable
