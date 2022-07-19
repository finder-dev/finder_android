package com.android.finder.viewmodel

import android.content.Context
import android.util.Log
import androidx.databinding.ObservableArrayList
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.finder.caching.CachingData
import com.android.finder.dataobj.CommunityHotTitleData
import com.android.finder.dataobj.DebateHotVO
import com.android.finder.network.MainNetWorkUtil
import com.android.finder.network.response.MessageResponse
import com.google.gson.Gson

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