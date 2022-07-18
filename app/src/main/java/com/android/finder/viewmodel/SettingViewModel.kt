package com.android.finder.viewmodel

import androidx.lifecycle.ViewModel
import com.android.finder.network.MainNetWorkUtil

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