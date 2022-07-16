package com.android.finder.viewmodel

import android.util.Log
import androidx.databinding.ObservableArrayList
import androidx.lifecycle.ViewModel
import com.android.finder.dataobj.Content
import com.android.finder.network.MainNetWorkUtil

class SaveViewModel : ViewModel() {

    val contentList : ObservableArrayList<Content> = ObservableArrayList()
    var currentPage = 0
    var isLast = false

    fun getSaveCommunityList() {
        MainNetWorkUtil.api.getUserSaveCommunityContent(currentPage).runCatching {
            val result = this.execute()
            if(result.isSuccessful) {
                if(currentPage == 0) contentList.clear()
                Log.e("body", result.body()?.toString().toString())
                result.body()?.response?.let {
                    contentList.addAll(it.contents)
                    isLast = it.last
                    currentPage++
                }
            }
        }.onFailure { it.printStackTrace() }
    }
}