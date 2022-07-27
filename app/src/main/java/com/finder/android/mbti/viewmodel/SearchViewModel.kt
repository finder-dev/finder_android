package com.finder.android.mbti.viewmodel

import androidx.databinding.ObservableArrayList
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.finder.android.mbti.App
import com.finder.android.mbti.R
import com.finder.android.mbti.database.DataBaseUtil
import com.finder.android.mbti.database.entity.SearchWordEntity
import com.finder.android.mbti.dataobj.Content
import com.finder.android.mbti.enumdata.CommunityOrderBy
import com.finder.android.mbti.network.MainNetWorkUtil

class SearchViewModel: ViewModel() {

    val contentList : ObservableArrayList<Content> = ObservableArrayList()
    var currentPage : Int = 0
    var isLast : Boolean = false
    var orderBy : MutableLiveData<CommunityOrderBy> = MutableLiveData(CommunityOrderBy.CREATE_TIME)
    var getListResultMessage = ""
    var searchWordList : ObservableArrayList<SearchWordEntity> = ObservableArrayList()

    fun getSearchList(searchWord : String) : Boolean {
        MainNetWorkUtil.api.getCommunitySearchList(searchWord = searchWord, orderBy = orderBy.value?.orderBy, page = currentPage).runCatching {
            val result = this.execute()
            if(result.isSuccessful) {
                if(currentPage == 0) contentList.clear()
                result.body()?.response?.let {
                    contentList.addAll(it.contents)
                    isLast = it.last
                    currentPage++
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

    fun getSearchWord() {
        searchWordList.clear()
        searchWordList.addAll(DataBaseUtil.getList())
    }

    fun likeChange(communityId: Long) : Boolean {
        MainNetWorkUtil.api.likeChange(communityId).runCatching {
            val result = this.execute()
            return result.isSuccessful
        }
        return false
    }
}