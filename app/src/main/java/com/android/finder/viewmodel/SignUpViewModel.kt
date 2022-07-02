package com.android.finder.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.finder.R
import com.android.finder.enum.MBTI
import com.android.finder.network.SignNetworkUtil
import com.android.finder.network.response.EmailSignUpResponse
import com.android.finder.oneButtonDialogShow
import com.google.gson.Gson
import com.google.gson.JsonObject
import java.lang.Exception

class SignUpViewModel : ViewModel() {

    val isSendCode : MutableLiveData<Boolean> = MutableLiveData(false)
    val isTermsAgree : MutableLiveData<Boolean> = MutableLiveData(false)
    var sendCodeResultMessage = ""
    var signUpResultMessage = ""

    var email = ""
    var emailAuthCode = ""
    var password = ""
    var mbti = ""
    var nickname = ""

    fun sendEmailAuthCode(context : Context, email : String) {
        if(email.isNotEmpty()) {
            SignNetworkUtil.api.getAuthCodeByEmail(email).execute().runCatching {
                if(this.isSuccessful) {
                    this.body()?.let {
                        sendCodeResultMessage = if(it.success) it.response.message?:"코드가 전송되었습니다."
                        else {
                            if(it.errorResponse.errorMessages.isEmpty()) "이메일을 확인해주세요."
                            else it.errorResponse.errorMessages[0]
                        }
                    }
                }
                isSendCode.postValue(this.isSuccessful)
            }
        } else {
            sendCodeResultMessage = ""
            oneButtonDialogShow(context, "email을 입력해주세요.")
        }
    }

    fun signUpByEmail(context: Context) : Boolean {
        if(MBTI.getMbtiByString(mbti) != null) {
            SignNetworkUtil.api.signUpByEmail(
                email,
                password,
                mbti,
                nickname
            ).runCatching {
                val result = this.execute()
                if(result.isSuccessful) {
                    result.body()?.let {
                        //여기서 토큰 처리
                    }
                } else {
                    result.errorBody()?.string()?.let {
                        try {
                            val response = Gson().fromJson(it, EmailSignUpResponse::class.java)
                            signUpResultMessage = if(response.errorResponse.errorMessages.isNotEmpty()) {
                                response.errorResponse.errorMessages[0]
                            } else {
                                "입력값을 확인해주세요."
                            }
                        } catch (e: Exception) {
                            signUpResultMessage = context.resources.getString(R.string.error_unspecified_message)
                            e.printStackTrace()
                        }
                    }
                }
                return result.isSuccessful
            }.onFailure {
                signUpResultMessage = context.resources.getString(R.string.error_unspecified_message)
                it.printStackTrace()
            }
        }
        return false
    }
}