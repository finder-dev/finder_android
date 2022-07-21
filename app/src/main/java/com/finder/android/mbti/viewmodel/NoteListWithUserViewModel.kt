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

class NoteListWithUserViewModel: ViewModel() {

    var targetUserData : NoteListData? = null
    val noteList : ObservableArrayList<UserNoteVO> = ObservableArrayList()
    var currentPage : Int = 0
    var getListResultMessage = ""
    var isLast : Boolean = false

    fun getNotes() : Boolean {
        targetUserData?.let {
            MainNetWorkUtil.api.getNoteWithUser(it.targetUserId, currentPage).runCatching {
                val result = this.execute()
                Log.e("안오네", result.isSuccessful.toString())
                if(result.isSuccessful) {
                    result.body()?.response?.let { note ->
                        Log.e("note", note.toString())
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
}