package com.finder.android.mbti.viewmodel

import android.content.Context
import androidx.databinding.ObservableArrayList
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.finder.android.mbti.caching.CachingData
import com.finder.android.mbti.dataobj.Content
import com.finder.android.mbti.network.MainNetWorkUtil

class MyViewModel: ViewModel() {

    val isExistProfile : MutableLiveData<Boolean> = MutableLiveData(false)
    var currentPage = 0
    val contentList : ObservableArrayList<Content> = ObservableArrayList()
    var isLast = false

    fun getProfile(context : Context) {
        if(CachingData.userProfile == null) {
            CachingData.setProfile(context)
        }
        isExistProfile.postValue(CachingData.userProfile != null)
    }

    fun getMyCommunityList() : Boolean {
        MainNetWorkUtil.api.getMyCommunityContentList(currentPage).runCatching {
            val result = this.execute()
            if(result.isSuccessful) {
                if(currentPage == 0) contentList.clear()
                result.body()?.response?.let {
                    contentList.addAll(it.contents)
                    isLast = it.last
                    currentPage++
                }
            }
            return result.isSuccessful
        }.onFailure { it.printStackTrace() }
        return false
    }

    fun getMyCommunityListThroughComment() : Boolean {
        MainNetWorkUtil.api.getMyCommunityContentListThroughComment(currentPage).runCatching {
            val result = this.execute()
            if(result.isSuccessful) {
                if(currentPage == 0) contentList.clear()
                result.body()?.response?.let {
                    contentList.addAll(it.contents)
                    isLast = it.last
                    currentPage++
                }
            }
            return result.isSuccessful
        }.onFailure { it.printStackTrace() }
        return false
    }

    fun likeChange(communityId: Long) : Boolean {
        MainNetWorkUtil.api.likeChange(communityId).runCatching {
            val result = this.execute()
            return result.isSuccessful
        }
        return false
    }

    fun logout() : Boolean {
        MainNetWorkUtil.api.logout().runCatching {
            return this.execute().isSuccessful
        }.onFailure { it.printStackTrace() }
        return false
    }
}