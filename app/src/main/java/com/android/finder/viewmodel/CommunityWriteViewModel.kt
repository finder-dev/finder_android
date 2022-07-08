package com.android.finder.viewmodel

import android.util.Log
import androidx.databinding.ObservableArrayList
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.finder.App
import com.android.finder.R
import com.android.finder.convertToMultipart
import com.android.finder.network.MainNetWorkUtil
import com.android.finder.network.response.EmailLoginResponse
import com.android.finder.network.response.MessageResponse
import com.google.gson.Gson
import okhttp3.RequestBody
import java.io.File

class CommunityWriteViewModel: ViewModel() {
    var isCurious : Boolean = false
    val selectedMbti : MutableLiveData<String> = MutableLiveData("")
    val questionImages : ObservableArrayList<File> = ObservableArrayList()
    var writeResultMessage = ""

    fun writeContent(title : String, content : String) : Boolean {
        if((selectedMbti.value?:"").isNotEmpty()) {
            val params = LinkedHashMap<String, Any>()
            params["title"] = title
            params["content"] = content
            params["mbti"] = selectedMbti.value!!
            params["isQuestion"] = isCurious
            if(!questionImages.isEmpty()) params["images"] = questionImages.map { it.path }
            val parts = convertToMultipart(params, "questionImgs[]")
            MainNetWorkUtil.api.writeCommunityContents(parts.first, parts.second).runCatching {
                val result = this.execute()
                if(!result.isSuccessful) {
                    result.errorBody()?.string()?.let {
                        Log.e("error", it)
                        val response = Gson().fromJson(it, MessageResponse::class.java)
                        Log.e("error", response.toString())
                        writeResultMessage = if(response.errorResponse.errorMessages.isNotEmpty()) {
                            response.errorResponse.errorMessages[0]
                        } else "입력값을 확인해주세요."

                    }
                } else {
                    writeResultMessage = App.instance.getString(R.string.msg_success_write)
                }
                return result.isSuccessful
            }.onFailure {
                writeResultMessage =App.instance.resources.getString(R.string.error_unspecified_message)
                it.printStackTrace()
            }
        } else {
            writeResultMessage = App.instance.resources.getString(R.string.error_mbti_empty)
        }
        return false
    }
}