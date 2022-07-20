package com.finder.android.mbti.network.response

import com.finder.android.mbti.dataobj.NoteWithUserVO
import com.google.gson.annotations.SerializedName

data class NoteListWithUserResponse(
    @SerializedName("content") val content : List<NoteWithUserVO>,
    @SerializedName("last") val last : Boolean
)