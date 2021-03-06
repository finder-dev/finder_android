package com.finder.android.mbti.viewmodel

import androidx.databinding.ObservableArrayList
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.finder.android.mbti.R
import com.finder.android.mbti.App
import com.finder.android.mbti.dataobj.Content
import com.finder.android.mbti.enumdata.CommunityOrderBy
import com.finder.android.mbti.network.MainNetWorkUtil
import com.finder.android.mbti.network.response.MessageResponse
import com.google.gson.Gson

class CommunityViewModel : ViewModel() {
    val contentList : ObservableArrayList<Content> = ObservableArrayList()
    var currentPage : Int = 0
    var isLast : Boolean = false
    var orderBy : MutableLiveData<CommunityOrderBy> = MutableLiveData(CommunityOrderBy.CREATE_TIME)
    var getListResultMessage = ""
    val mbti : MutableLiveData<String> = MutableLiveData("전체")

    fun getCommunityList() : Boolean {
        MainNetWorkUtil.api.getCommunityPostList(if(mbti.value =="전체") null else mbti.value, orderBy = orderBy.value?.orderBy, page = currentPage).runCatching {
            val result = this.execute()
            if(result.isSuccessful) {
                if(currentPage == 0) contentList.clear()
                result.body()?.response?.let {
                    contentList.addAll(it.contents)
                    isLast = it.last
                    currentPage++
                }
            } else {
                MainNetWorkUtil.errorMessage(result.errorBody())?.let {
                    getListResultMessage = if(it.errorResponse.errorMessages.isNotEmpty()) {
                        it.errorResponse.errorMessages[0]
                    } else "입력값을 확인해주세요."
                }
            }
            return result.isSuccessful
        }.onFailure {
            getListResultMessage = App.instance.resources.getString(R.string.error_unspecified_message)
            it.printStackTrace()
        }
        return false
    }

    fun likeChange(communityId: Long) : Boolean {
        MainNetWorkUtil.api.likeChange(communityId).runCatching {
            val result = this.execute()
            return result.isSuccessful
        }
        return false
    }
}