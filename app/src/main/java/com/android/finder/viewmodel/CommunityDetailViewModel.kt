package com.android.finder.viewmodel

import android.util.Log
import androidx.databinding.ObservableArrayList
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.finder.App
import com.android.finder.dataobj.CommunityDetailDto
import com.android.finder.network.MainNetWorkUtil
import com.android.finder.network.response.MessageResponse
import com.google.gson.Gson
import com.android.finder.R
import com.android.finder.dataobj.CommentData
import java.io.File

class CommunityDetailViewModel: ViewModel() {

    val communityDetailData : MutableLiveData<CommunityDetailDto?> = MutableLiveData()
    val questionImages : ObservableArrayList<String> = ObservableArrayList()
    val commentList : ObservableArrayList<CommentData> = ObservableArrayList()
    var detailResultMessage = ""
    var answerId = 0

    fun getCommunityContentDetail(communityId : Long) {
        MainNetWorkUtil.api.getCommunityDetail(communityId).runCatching {
            val result = this.execute()
            if(result.isSuccessful) {
                result.body()?.let {
                    communityDetailData.postValue(it.response)
                    questionImages.apply {
                        clear()
                        addAll(it.response.communityImgDtos.map {  img -> img.communityImageUrl })
                    }
                    commentList.apply {
                        clear()
                        addAll(it.response.answerHistDtos)
                    }
                }
            } else {
                result.errorBody()?.string()?.let {
                    val response = Gson().fromJson(it, MessageResponse::class.java)
                    detailResultMessage =  if(response.errorResponse.errorMessages.isNotEmpty()) {
                        response.errorResponse.errorMessages[0]
                    } else App.instance.resources.getString(R.string.error_community_detail_load_sub)

                }
            }
        }.onFailure {
            detailResultMessage = App.instance.resources.getString(R.string.error_unspecified_message)
            it.printStackTrace()
        }
    }

    fun createAnswer(communityId: Long, content : String) : Boolean {
        var result = false
        MainNetWorkUtil.api.createAnswer(communityId, content).runCatching {
            val data = this.execute()
            if(!data.isSuccessful) {
                data.errorBody()?.string()?.let {
                    Log.e("error?", it)
                }
            }
            result = data.isSuccessful
        }
            .onFailure {
                it.printStackTrace()
            }
        return result
    }

    fun likeChange(communityId: Long) : Boolean {
        var result = false
        MainNetWorkUtil.api.likeChange(communityId).runCatching { result = this.execute().isSuccessful }
            .onFailure {
                it.printStackTrace()
            }
        return result
    }

    fun saveChange(communityId: Long) : Boolean {
        var result = false
        MainNetWorkUtil.api.saveChange(communityId).runCatching { result = this.execute().isSuccessful }
            .onFailure {
                it.printStackTrace()
            }
        return result
    }
}