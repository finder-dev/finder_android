package com.finder.android.mbti.viewmodel

import androidx.lifecycle.ViewModel
import com.finder.android.mbti.R
import com.finder.android.mbti.App
import com.finder.android.mbti.network.MainNetWorkUtil
import com.finder.android.mbti.network.request.CreateDebateRequestDTO
import com.finder.android.mbti.network.response.MessageResponse
import com.google.gson.Gson

class DebateWriteViewModel : ViewModel() {

    fun createDebate(title : String, optionA : String, optionB : String) : String {
        var message = ""
        MainNetWorkUtil.api.createDebate(CreateDebateRequestDTO(title, optionA, optionB)).runCatching {
            val result = this.execute()
            if(result.isSuccessful) {
                message = App.instance.resources.getString(R.string.msg_success_create_debate)
            } else {
                result.errorBody()?.string()?.let {
                    val response = Gson().fromJson(it, MessageResponse::class.java)
                    message = if(response.errorResponse.errorMessages.isNotEmpty()) {
                        response.errorResponse.errorMessages[0]
                    } else App.instance.resources.getString(R.string.error_create_debate)
                }
            }
        }
        return message
    }
}