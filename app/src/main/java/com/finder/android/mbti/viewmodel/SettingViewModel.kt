package com.finder.android.mbti.viewmodel

import androidx.lifecycle.ViewModel
import com.finder.android.mbti.network.MainNetWorkUtil

class SettingViewModel: ViewModel() {

    fun deletionUser() : Boolean {
        MainNetWorkUtil.api.accountDeletion().runCatching {
            val result = this.execute()
            return result.isSuccessful
        }.onFailure {
            it.printStackTrace()
        }
        return false
    }
}