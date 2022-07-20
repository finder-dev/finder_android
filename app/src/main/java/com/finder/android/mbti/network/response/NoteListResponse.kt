package com.finder.android.mbti.network.response

import com.finder.android.mbti.dataobj.NoteListData
import com.google.gson.annotations.SerializedName

data class NoteListResponse(
    @SerializedName("content") val noteList : List<NoteListData>,
    @SerializedName("last") val last : Boolean
)
