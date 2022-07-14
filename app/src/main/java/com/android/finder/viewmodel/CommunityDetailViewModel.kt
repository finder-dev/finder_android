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
    var deleteContentResultMessage = ""
    var reportContentResultMessage = ""
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

    fun deleteAnswers(answerId : Long) : String {
        var message = ""
        MainNetWorkUtil.api.deleteCommentAndReComment(answerId).runCatching {
            val data = this.execute()
            if(!data.isSuccessful) {
                data.errorBody()?.string()?.let {
                    val response = Gson().fromJson(it, MessageResponse::class.java)
                    message = if(response.errorResponse.errorMessages.isNotEmpty()) {
                        response.errorResponse.errorMessages[0]
                    } else App.instance.resources.getString(R.string.error_comment_delete)
                }
            }
        }
        return message
    }

    fun reportAnswers(answerId : Long) : String {
        var message = ""
        MainNetWorkUtil.api.reportCommentAndReComment(answerId).runCatching {
            val data = this.execute()
            if(!data.isSuccessful) {
                data.errorBody()?.string()?.let {
                    val response = Gson().fromJson(it, MessageResponse::class.java)
                    message = if(response.errorResponse.errorMessages.isNotEmpty()) {
                        response.errorResponse.errorMessages[0]
                    } else App.instance.resources.getString(R.string.error_comment_delete)
                }
            }
        }
        return message
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

    fun deleteCommunityContent(communityId: Long) : Boolean {
        var result = false
        MainNetWorkUtil.api.deleteCommunityContent(communityId).runCatching {
            val data = this.execute()
            result = data.isSuccessful
            if(data.isSuccessful) {
                deleteContentResultMessage = App.instance.getString(R.string.msg_delete_content)
            } else {
                data.errorBody()?.string()?.let {
                    val response = Gson().fromJson(it, MessageResponse::class.java)
                    deleteContentResultMessage = if(response.errorResponse.errorMessages.isNotEmpty()) {
                        response.errorResponse.errorMessages[0]
                    } else App.instance.resources.getString(R.string.error_delete_content_fail)
                }
            }
        }.onFailure {
            deleteContentResultMessage = App.instance.resources.getString(R.string.error_delete_content_fail)
            it.printStackTrace()
        }
        return result
    }

    fun reportContent(communityId: Long) : Boolean {
        var result = false
        MainNetWorkUtil.api.reportCommunityContent(communityId).runCatching {
            val data = this.execute()
            result = data.isSuccessful
            if(data.isSuccessful) {
                reportContentResultMessage = App.instance.getString(R.string.msg_report_complete)
            } else {
                data.errorBody()?.string()?.let {
                    val response = Gson().fromJson(it, MessageResponse::class.java)
                    reportContentResultMessage = if(response.errorResponse.errorMessages.isNotEmpty()) {
                        response.errorResponse.errorMessages[0]
                    } else App.instance.resources.getString(R.string.error_delete_content_fail)
                }
            }
        }.onFailure {
            reportContentResultMessage = App.instance.resources.getString(R.string.error_delete_content_fail)
            it.printStackTrace()
        }
        return result
    }
}