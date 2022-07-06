package com.android.finder.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.finder.R
import com.android.finder.network.SignNetworkUtil
import com.android.finder.network.response.EmailLoginResponse
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
                    Log.e("login success", it.toString())
                }
            } else {
                result.errorBody()?.string()?.let {
                    try {
                        val response = Gson().fromJson(it, EmailLoginResponse::class.java)
                        loginErrorMessage = if(response.errorResponse.errorMessages.isNotEmpty()) {
                            response.errorResponse.errorMessages[0]
                        } else {
                            "입력값을 확인해주세요."
                        }
                    } catch (e: Exception) {
                        loginErrorMessage = context.resources.getString(R.string.error_unspecified_message)
                        e.printStackTrace()
                    }
                }
                return result.isSuccessful
            }
        }.onFailure {
            loginErrorMessage = context.resources.getString(R.string.error_unspecified_message)
            it.printStackTrace()
        }
        return false
    }
}