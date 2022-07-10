package com.android.finder.viewmodel

import android.content.Context
import android.util.Log
import androidx.databinding.ObservableArrayList
import androidx.lifecycle.ViewModel
import com.android.finder.App
import com.android.finder.R
import com.android.finder.dataobj.Content
import com.android.finder.enum.CommunityOrderBy
import com.android.finder.network.MainNetWorkUtil
import com.android.finder.network.response.MessageResponse
import com.google.gson.Gson
import java.io.File

class CommunityViewModel : ViewModel() {
    val contentList : ObservableArrayList<Content> = ObservableArrayList()
    var currentPage : Int = 0
    var lastPage : Int = 0
    var orderBy : CommunityOrderBy = CommunityOrderBy.CREATE_TIME
    var getListResultMessage = ""

    fun getCommunityList(mbti :String? = null) : Boolean {
        currentPage++
        MainNetWorkUtil.api.getCommunityPostList(mbti = mbti, orderBy = orderBy.orderBy, page = currentPage).runCatching {
            val result = this.execute()
            Log.e("community result", result.isSuccessful.toString())
            if(result.isSuccessful) {
                if(currentPage == 1) contentList.clear()
                result.body()?.response?.let {
                    Log.e("community body", it.toString())
                    contentList.addAll(it.contents)
                    lastPage = it.totalPages
                }
            } else {
                result.errorBody()?.string()?.let {
                    Log.e("error", it)
                    val response = Gson().fromJson(it, MessageResponse::class.java)
                    getListResultMessage = if(response.errorResponse.errorMessages.isNotEmpty()) {
                        response.errorResponse.errorMessages[0]
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
}