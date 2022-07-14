package com.android.finder.dataobj

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class CommunityImgDto(
    @SerializedName("communityImageId") val communityImageId: Long,
    @SerializedName("communityImageUrl") val communityImageUrl: String
) : Parcelable