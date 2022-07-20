package com.finder.android.mbti.caching

import android.content.Context
import android.util.Log
import com.finder.android.mbti.R
import com.finder.android.mbti.App
import com.finder.android.mbti.network.MainNetWorkUtil
import com.finder.android.mbti.dataobj.UserProfile
import com.finder.android.mbti.oneButtonDialogShow

object CachingData {
    var userProfile: UserProfile? = null

    fun setProfile(context: Context) {
        MainNetWorkUtil.api.getUserProfile().runCatching {
            val result = this.execute()
            if (result.isSuccessful) {
                result.body()?.let {
                    Log.e("정보불러오기", it.response.toString())
                    userProfile = it.response
                }
            }
        }.onFailure { it.printStackTrace() }
        if (userProfile == null) {
            oneButtonDialogShow(
                context,
                App.instance.resources.getString(R.string.error_login),
                App.instance.resources.getString(
                    R.string.msg_re_login
                )
            )
        }
    }
}