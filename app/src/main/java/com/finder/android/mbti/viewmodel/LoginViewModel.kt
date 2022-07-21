package com.finder.android.mbti.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.finder.android.mbti.R
import com.finder.android.mbti.network.MainNetWorkUtil
import com.finder.android.mbti.network.SignNetworkUtil
import com.finder.android.mbti.toastShow
import com.finder.android.mbti.util.SecureManager
import java.lang.Exception

class LoginViewModel: ViewModel() {

    val isAutoLogin : MutableLiveData<Boolean> = MutableLiveData(true)
    var loginErrorMessage = ""

    fun login(context : Context, email :String, password : String) : Boolean {
        SignNetworkUtil.api.login(email, password).runCatching {
            val result = this.execute()
            Log.e("버근가...", email)
            if(result.isSuccessful) {
                //여기서 토큰 처리
                result.body()?.let {
                    it.response?.accessToken?.let { token ->
                        SecureManager(context).setToken(token)
                    }
                }
            } else {
                MainNetWorkUtil.errorMessage(result.errorBody())?.let {
                    try {
                        loginErrorMessage = if(it.errorResponse.errorMessages.isNotEmpty()) {
                            it.errorResponse.errorMessages[0]
                        } else {
                            "입력값을 확인해주세요."
                        }
                    } catch (e: Exception) {
                        loginErrorMessage = context.resources.getString(R.string.error_unspecified_message)
                        e.printStackTrace()
                    }
                }
            }
            return result.isSuccessful
        }.onFailure {
            loginErrorMessage = context.resources.getString(R.string.error_unspecified_message)
            it.printStackTrace()
        }
        return false
    }
}