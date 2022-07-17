package com.android.finder.caching

import android.content.Context
import android.util.Log
import com.android.finder.App
import com.android.finder.R
import com.android.finder.network.MainNetWorkUtil
import com.android.finder.dataobj.UserProfile
import com.android.finder.oneButtonDialogShow

object CachingData {
    var userProfile: UserProfile? = null

    fun setProfile(context: Context) {
        MainNetWorkUtil.api.getUserProfile().runCatching {
            val result = this.execute()
            if (result.isSuccessful) {
                result.body()?.let {
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