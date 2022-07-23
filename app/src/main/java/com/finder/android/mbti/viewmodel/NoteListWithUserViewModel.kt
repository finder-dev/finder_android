package com.finder.android.mbti.viewmodel

import android.util.Log
import androidx.databinding.ObservableArrayList
import androidx.lifecycle.ViewModel
import com.finder.android.mbti.App
import com.finder.android.mbti.R
import com.finder.android.mbti.dataobj.NoteListData
import com.finder.android.mbti.dataobj.NoteWithUserVO
import com.finder.android.mbti.dataobj.UserNoteVO
import com.finder.android.mbti.network.MainNetWorkUtil
import com.finder.android.mbti.network.request.BlockUserRequest
import com.finder.android.mbti.network.request.DeleteUserRequestDTO
import com.finder.android.mbti.network.request.ReportUserRequest

class NoteListWithUserViewModel: ViewModel() {

    var targetUserData : NoteListData? = null
    val noteList : ObservableArrayList<UserNoteVO> = ObservableArrayList()
    var currentPage : Int = 0
    var getListResultMessage = ""
    var isLast : Boolean = false
    var reportUserResultMessage = ""
    var blockUserResultMessage = ""
    var deleteUserNoteResultMessage = ""

    fun getNotes() : Boolean {
        targetUserData?.let {
            MainNetWorkUtil.api.getNoteWithUser(it.targetUserId, currentPage).runCatching {
                val result = this.execute()
                if(result.isSuccessful) {
                    result.body()?.response?.let { note ->
                        if(currentPage == 0) noteList.clear()
                        isLast = note.last
                        noteList.addAll(note.content.map { userVo ->
                            UserNoteVO(userVo.fromUserId != it.targetUserId, userVo.content, userVo.createTime)
                        })
                        currentPage++
                    }
                } else {
                    MainNetWorkUtil.errorMessage(result.errorBody())?.let { message ->
                        getListResultMessage = if(message.errorResponse.errorMessages.isNotEmpty()) {
                            message.errorResponse.errorMessages[0]
                        } else App.instance.resources.getString(R.string.error_unspecified_message)
                    }
                }
                return result.isSuccessful
            }.onFailure { error ->
                getListResultMessage = App.instance.resources.getString(R.string.error_unspecified_message)
                error.printStackTrace()
            }
        }
        return false
    }

    fun reportUser(userId: Long) : Boolean {
        reportUserResultMessage = App.instance.resources.getString(R.string.error_unspecified_message)
        MainNetWorkUtil.api.reportUser(ReportUserRequest(userId)).runCatching {
            val result = this.execute()
            if(result.isSuccessful) {
                reportUserResultMessage = App.instance.getString(R.string.msg_report_complete)
            } else {
                MainNetWorkUtil.errorMessage(result.errorBody())?.let {
                    if(it.errorResponse.errorMessages.isNotEmpty()) {
                        reportUserResultMessage = it.errorResponse.errorMessages[0]
                    }
                }
            }
            return result.isSuccessful
        }.onFailure {
            it.printStackTrace()
        }
        return false
    }

    fun blockUser(userId: Long) : Boolean {
        blockUserResultMessage = App.instance.resources.getString(R.string.error_unspecified_message)
        MainNetWorkUtil.api.blockUser(BlockUserRequest(userId)).runCatching {
            val result = this.execute()
            if(result.isSuccessful) {
                blockUserResultMessage = App.instance.getString(R.string.msg_block_complete)
            } else {
                MainNetWorkUtil.errorMessage(result.errorBody())?.let {
                    if(it.errorResponse.errorMessages.isNotEmpty()) {
                        blockUserResultMessage = it.errorResponse.errorMessages[0]
                    }
                }
            }
            return result.isSuccessful
        }.onFailure {
            it.printStackTrace()
        }
        return false
    }

    fun deleteUserNote(userId: Long) : Boolean {
        deleteUserNoteResultMessage = App.instance.resources.getString(R.string.error_unspecified_message)
        MainNetWorkUtil.api.allDeleteNoteWithUser(DeleteUserRequestDTO(userId)).runCatching {
            val result = this.execute()
            if(result.isSuccessful) {
                deleteUserNoteResultMessage = App.instance.getString(R.string.msg_delete_complete)
            } else {
                MainNetWorkUtil.errorMessage(result.errorBody())?.let {
                    if(it.errorResponse.errorMessages.isNotEmpty()) {
                        deleteUserNoteResultMessage = it.errorResponse.errorMessages[0]
                    }
                }
            }
            return result.isSuccessful
        }.onFailure {
            it.printStackTrace()
        }
        return false
    }
}