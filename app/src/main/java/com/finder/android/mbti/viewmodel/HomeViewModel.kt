package com.finder.android.mbti.viewmodel

import android.content.Context
import androidx.databinding.ObservableArrayList
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.finder.android.mbti.caching.CachingData
import com.finder.android.mbti.dataobj.CommunityHotTitleData
import com.finder.android.mbti.dataobj.DebateHotVO
import com.finder.android.mbti.network.MainNetWorkUtil

class HomeViewModel: ViewModel() {

    val isExistProfile : MutableLiveData<Boolean> = MutableLiveData(false)
    val communityHotList : ObservableArrayList<CommunityHotTitleData> = ObservableArrayList()
    val hotDebate : MutableLiveData<DebateHotVO?> = MutableLiveData(null)
    val isDebateJoin : MutableLiveData<Boolean> = MutableLiveData(false)
    var isA = false

    fun getProfile(context : Context) {
        if(CachingData.userProfile == null) {
            CachingData.setProfile(context)
        }
        isExistProfile.postValue(CachingData.userProfile != null)
    }

    fun getHotList() {
        MainNetWorkUtil.api.getCommunityHotList().runCatching {
            val result = this.execute()
            if(result.isSuccessful) {
                result.body()?.let {
                    communityHotList.clear()
                    communityHotList.addAll(it.response)
                }
            }
        }
    }

    fun getDebateHot() {
        MainNetWorkUtil.api.getDebateHot().runCatching {
            val result = this.execute()
            if(result.isSuccessful) {
                result.body()?.let {
                    hotDebate.postValue(it.response)
                    isDebateJoin.postValue(it.response.join)
                    if(it.response.joinOption != null) {
                        isA = (it.response.joinOption == "A")
                    }
                }
            }
        }.onFailure { it.printStackTrace() }
    }
}