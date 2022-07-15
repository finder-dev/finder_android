package com.android.finder.viewmodel

import android.util.Log
import androidx.databinding.ObservableArrayList
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.finder.App
import com.android.finder.R
import com.android.finder.dataobj.DebateListVO
import com.android.finder.enumdata.DebateFilter
import com.android.finder.network.MainNetWorkUtil
import com.android.finder.network.response.MessageResponse
import com.google.gson.Gson

class DebateViewModel : ViewModel() {

    var currentPage: Int = 0
    var isLast: Boolean = false
    var currentFilter: MutableLiveData<DebateFilter> = MutableLiveData()
    val debateList : ObservableArrayList<DebateListVO> = ObservableArrayList()
    var debateListResultMessage = ""

    fun getDebateListFromServer() : Boolean {
        MainNetWorkUtil.api.getDebateList(
            state = (currentFilter.value ?: DebateFilter.PROCEEDING).filterString, currentPage
        ).runCatching {
            val result = this.execute()
            if(result.isSuccessful) {
                if(currentPage == 0) debateList.clear()
                result.body()?.let {
                    Log.e("body", it.toString())
                    debateList.addAll(it.debateResponse.content)
                    isLast = it.debateResponse.last
                    currentPage++
                }
            } else {
                result.errorBody()?.string()?.let {
                    val response = Gson().fromJson(it, MessageResponse::class.java)
                    debateListResultMessage = if(response.errorResponse.errorMessages.isNotEmpty()) {
                        response.errorResponse.errorMessages[0]
                    } else "입력값을 확인해주세요."
                }
            }
            return result.isSuccessful
        }.onFailure {
            debateListResultMessage = App.instance.resources.getString(R.string.error_unspecified_message)
            it.printStackTrace()
        }
        return false
    }
}