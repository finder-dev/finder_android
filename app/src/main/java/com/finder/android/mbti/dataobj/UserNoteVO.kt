package com.finder.android.mbti.dataobj

import com.google.gson.annotations.SerializedName

data class UserNoteVO(
    val isMynote : Boolean,
    val content : String,
    val createTime : String
)
