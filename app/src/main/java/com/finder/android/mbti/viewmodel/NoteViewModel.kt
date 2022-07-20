package com.finder.android.mbti.viewmodel

import androidx.databinding.ObservableArrayList
import androidx.lifecycle.ViewModel
import com.finder.android.mbti.App
import com.finder.android.mbti.R
import com.finder.android.mbti.dataobj.NoteListData
import com.finder.android.mbti.network.MainNetWorkUtil

class NoteViewModel: ViewModel() {

    val noteList : ObservableArrayList<NoteListData> = ObservableArrayList()
    var currentPage : Int = 0
    var getListResultMessage = ""
    var isLast : Boolean = false

    fun getAllNoteList() : Boolean {
        MainNetWorkUtil.api.getAllNotes(currentPage).runCatching {
            val result = this.execute()
            if(result.isSuccessful) {
                result.body()?.response?.let {
                    if(currentPage == 0) noteList.clear()
                    isLast = it.last
                    currentPage++
                    noteList.addAll(it.noteList)
                }
            } else {
                MainNetWorkUtil.errorMessage(result.errorBody())?.let {
                    getListResultMessage = if(it.errorResponse.errorMessages.isNotEmpty()) {
                        it.errorResponse.errorMessages[0]
                    } else App.instance.resources.getString(R.string.error_unspecified_message)
                }
            }
            return result.isSuccessful
        }.onFailure {
            getListResultMessage = App.instance.resources.getString(R.string.error_unspecified_message)
            it.printStackTrace()
        }
        return false
    }
}