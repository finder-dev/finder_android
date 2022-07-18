package com.android.finder.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.finder.R
import com.android.finder.network.MainNetWorkUtil
import com.android.finder.network.SignNetworkUtil
import com.android.finder.network.response.EmailLoginResponse
import com.android.finder.toastShow
import com.android.finder.util.SecureManager
import com.google.gson.Gson
import java.lang.Exception

class LoginViewModel: ViewModel() {

    val isAutoLogin : MutableLiveData<Boolean> = MutableLiveData(true)
    var loginErrorMessage = ""

    fun login(context : Context, email :String, password : String) : Boolean {
        SignNetworkUtil.api.login(email, password).runCatching {
            val result = this.execute()
            if(result.isSuccessful) {
                //여기서 토큰 처리
                result.body()?.let {
                    toastShow(context, it.response?.toString().toString())
                    it.response?.accessToken?.let { token ->
                        SecureManager(context).setToken(token)
                    }
                }
            } else {
                MainNetWorkUtil.errorMessage(result.errorBody())?.let {
                    try {
                        toastShow(context, it.toString())
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