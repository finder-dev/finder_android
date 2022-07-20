package com.finder.android.mbti.viewmodel

import androidx.databinding.ObservableArrayList
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.finder.android.mbti.R
import com.finder.android.mbti.App
import com.finder.android.mbti.dataobj.DebateListVO
import com.finder.android.mbti.enumdata.DebateFilter
import com.finder.android.mbti.network.MainNetWorkUtil
import com.finder.android.mbti.network.response.MessageResponse
import com.google.gson.Gson

class DebateViewModel : ViewModel() {

    var currentPage: Int = 0
    var isLast: Boolean = false
    var currentFilter: MutableLiveData<DebateFilter> = MutableLiveData(DebateFilter.PROCEEDING)
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