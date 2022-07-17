package com.android.finder.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.finder.App
import com.android.finder.R
import com.android.finder.caching.CachingData
import com.android.finder.network.MainNetWorkUtil
import com.android.finder.network.SignNetworkUtil
import com.android.finder.network.request.ModifyUserInformationRequestDTO
import com.android.finder.network.response.EmailLoginResponse
import com.android.finder.network.response.MessageResponse
import com.google.gson.Gson

class UserInformationViewModel : ViewModel() {

    val isExistProfile: MutableLiveData<Boolean> = MutableLiveData(false)
    var isA = false

    var checkNicknameResultMessage = App.instance.resources.getString(R.string.error_unspecified_message)
    var mbti = ""

    var modifyUserInfoResultMessage = App.instance.getString(R.string.error_unspecified_message)

    fun getProfile(context: Context) {
        if (CachingData.userProfile == null) {
            CachingData.setProfile(context)
        }
        isExistProfile.postValue(CachingData.userProfile != null)
    }

    fun checkNickname(nickname: String) : Boolean{
        MainNetWorkUtil.api.checkDuplicateNickname(nickname).runCatching {
            val result = this.execute()
            if (result.isSuccessful) {
                result.body()?.response?.message?.let { checkNicknameResultMessage = it }
            } else {
                result.errorBody()?.string()?.let {
                    val response = Gson().fromJson(it, EmailLoginResponse::class.java)
                    if (response.errorResponse.errorMessages.isNotEmpty()) {
                        checkNicknameResultMessage = response.errorResponse.errorMessages[0]
                    }
                }
            }
            return result.isSuccessful
        }.onFailure {
            it.printStackTrace()
        }
        return false
    }

    fun modifyUserInformation(nickname: String, mbti : String, password :String? = null) : Boolean {
        MainNetWorkUtil.api.modifyUserInformation(
            ModifyUserInformationRequestDTO(
                nickname, mbti, password
            )
        ).runCatching {
            val result = this.execute()
            if(result.isSuccessful) {
                modifyUserInfoResultMessage = App.instance.resources.getString(R.string.msg_modify)
            } else {
                MainNetWorkUtil.errorMessage(result.errorBody())?.let {
                    if(it.errorResponse.errorMessages.isNotEmpty()) {
                        modifyUserInfoResultMessage = it.errorResponse.errorMessages[0]
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