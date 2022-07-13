package com.android.finder.viewmodel

import android.content.Context
import androidx.databinding.ObservableArrayList
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.finder.caching.CachingData
import com.android.finder.dataobj.CommunityHotTitleData

class HomeViewModel: ViewModel() {

    val isExistProfile : MutableLiveData<Boolean> = MutableLiveData(false)
    val communityHotList : ObservableArrayList<CommunityHotTitleData> = ObservableArrayList()

    fun getProfile(context : Context) {
        if(CachingData.userProfile == null) {
            CachingData.setProfile(context)
        }
        isExistProfile.postValue(CachingData.userProfile != null)
    }
}