package com.android.finder.network.response

import com.android.finder.dataobj.DebateListVO

data class DebateResponse(
    val content: List<DebateListVO>,
    val last: Boolean
)