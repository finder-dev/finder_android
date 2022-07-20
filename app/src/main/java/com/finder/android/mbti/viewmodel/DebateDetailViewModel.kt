package com.finder.android.mbti.viewmodel

import androidx.databinding.ObservableArrayList
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.finder.android.mbti.R
import com.finder.android.mbti.App
import com.finder.android.mbti.dataobj.CommentData
import com.finder.android.mbti.network.MainNetWorkUtil
import com.finder.android.mbti.network.request.ContentBodyRequestDTO
import com.finder.android.mbti.network.request.DebateOptionBodyRequestDTO
import com.finder.android.mbti.network.response.DebateDetailResponseVO
import com.finder.android.mbti.network.response.MessageResponse
import com.google.gson.Gson

class DebateDetailViewModel: ViewModel() {

    val commentList : ObservableArrayList<CommentData> = ObservableArrayList()
    val detailData : MutableLiveData<DebateDetailResponseVO?> = MutableLiveData()
    var detailResultMessage = ""

    fun getDebateDetail(debateId : Long) {
        MainNetWorkUtil.api.getDebateDetail(debateId).runCatching {
            val result = this.execute()
            if(result.isSuccessful) {
                result.body()?.let {
                    detailData.postValue(it.response)
                    commentList.apply {
                        clear()
                        this.addAll(it.response.commentList)
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

    fun createAnswer(debateId: Long, content : String) : String {
        var message = ""
        MainNetWorkUtil.api.createDebateComment(debateId, ContentBodyRequestDTO(content)).runCatching {
            val data = this.execute()
            if(!data.isSuccessful) {
                data.errorBody()?.string()?.let {
                    val response = Gson().fromJson(it, MessageResponse::class.java)
                    message = if(response.errorResponse.errorMessages.isNotEmpty()) {
                        response.errorResponse.errorMessages[0]
                    } else App.instance.resources.getString(R.string.error_comment_add)
                }
            }
        }.onFailure {
            message = App.instance.resources.getString(R.string.error_unspecified_message)
                it.printStackTrace()
            }
        return message
    }

    fun reportContent(debateId : Long) : String {
        var message = ""
        MainNetWorkUtil.api.reportDebateContent(debateId).runCatching {
            val data = this.execute()
            if(data.isSuccessful) {
                message = App.instance.getString(R.string.msg_report_complete)
            } else {
                data.errorBody()?.string()?.let {
                    val response = Gson().fromJson(it, MessageResponse::class.java)
                    message = if(response.errorResponse.errorMessages.isNotEmpty()) {
                        response.errorResponse.errorMessages[0]
                    } else App.instance.resources.getString(R.string.error_delete_content_fail)
                }
            }
        }.onFailure {
            message = App.instance.resources.getString(R.string.error_delete_content_fail)
            it.printStackTrace()
        }
        return message
    }

    fun modifyAnswers(answerId: Long, content: String) : String {
        var message = ""
        MainNetWorkUtil.api.modifyDebateCommentAndReComment(answerId, ContentBodyRequestDTO(content)).runCatching {
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

    fun createReAnswer(answerId: Long, content: String) : String {
        var message = ""
        MainNetWorkUtil.api.createDebateReComment(answerId, ContentBodyRequestDTO(content)).runCatching {
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

    fun deleteAnswers(answerId : Long) : String {
        var message = ""
        MainNetWorkUtil.api.deleteDebateCommentAndReComment(answerId).runCatching {
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
        MainNetWorkUtil.api.reportDebateCommentAndReComment(answerId).runCatching {
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

    fun joinDebate(debateId: Long, option : String) : String {
        var message = ""
        MainNetWorkUtil.api.joinDebateSelect(debateId, DebateOptionBodyRequestDTO(option)).runCatching {
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
}