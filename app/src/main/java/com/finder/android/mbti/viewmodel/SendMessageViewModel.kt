package com.finder.android.mbti.viewmodel

import androidx.lifecycle.ViewModel
import com.finder.android.mbti.App
import com.finder.android.mbti.R
import com.finder.android.mbti.network.MainNetWorkUtil
import com.finder.android.mbti.network.request.SendANoteRequestDTO

class SendMessageViewModel : ViewModel() {

    var sendResultMessage = ""

    fun sendANote(userId: Long, content: String) : Boolean {
        MainNetWorkUtil.api.sendAMessage(SendANoteRequestDTO(userId, content)).runCatching {
            val result = this.execute()
            if (result.isSuccessful) {
                sendResultMessage = result.body()?.response?.message
                    ?: App.instance.getString(R.string.msg_message_send)
            } else {
                MainNetWorkUtil.errorMessage(result.errorBody())?.let {
                    sendResultMessage = if (it.errorResponse.errorMessages.isNotEmpty()) {
                        it.errorResponse.errorMessages[0]
                    } else App.instance.resources.getString(R.string.error_community_detail_load_sub)
                }
            }
            return result.isSuccessful
        }.onFailure {
            it.printStackTrace()
            sendResultMessage = App.instance.resources.getString(R.string.error_community_detail_load_sub)
        }
        return false
    }
}