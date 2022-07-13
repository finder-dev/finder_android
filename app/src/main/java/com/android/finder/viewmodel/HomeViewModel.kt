package com.android.finder.viewmodel

import android.content.Context
import android.util.Log
import androidx.databinding.ObservableArrayList
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.finder.caching.CachingData
import com.android.finder.dataobj.CommunityHotTitleData
import com.android.finder.network.MainNetWorkUtil

class HomeViewModel: ViewModel() {

    val isExistProfile : MutableLiveData<Boolean> = MutableLiveData(false)
    val communityHotList : ObservableArrayList<CommunityHotTitleData> = ObservableArrayList()

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
                    Log.e("response", communityHotList.toString())
                }
            }
        }
    }
}