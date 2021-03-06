package com.finder.android.mbti.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.finder.android.mbti.R
import com.finder.android.mbti.enumdata.MBTI
import com.finder.android.mbti.network.SignNetworkUtil
import com.finder.android.mbti.network.response.EmailCodeCheckResponse
import com.finder.android.mbti.network.response.EmailLoginResponse
import com.finder.android.mbti.network.response.MessageResponse
import com.finder.android.mbti.oneButtonDialogShow
import com.finder.android.mbti.util.SecureManager
import com.google.gson.Gson
import java.lang.Exception

class SignUpViewModel : ViewModel() {

    val isSendCode: MutableLiveData<Boolean> = MutableLiveData(false)
    val isCheckCodeComplete: MutableLiveData<Boolean> = MutableLiveData(false)
    val isTermsAgree: MutableLiveData<Boolean> = MutableLiveData(false)
    var sendCodeResultMessage = ""
    var signUpResultMessage = ""
    var checkCodeMessage = ""
    var duplicatedNicknameResultMessage = ""

    var email = ""
    var emailAuthCode = ""
    var password = ""
    var mbti : MutableLiveData<String?> = MutableLiveData()
    var nickname = ""

    fun sendEmailAuthCode(context: Context, email: String) {
        if (email.isNotEmpty()) {
            SignNetworkUtil.api.getAuthCodeByEmail(email).execute().runCatching {
                if (this.isSuccessful) {
                    this.body()?.let {
                        sendCodeResultMessage =
                            if (it.success) it.response?.message ?: "코드가 전송되었습니다."
                            else {
                                if (it.errorResponse.errorMessages.isEmpty()) "이메일을 확인해주세요."
                                else it.errorResponse.errorMessages[0]
                            }
                    }
                } else {
                    this.errorBody()?.string()?.let {
                        try {
                            Log.e("response", it)
                            val response = Gson().fromJson(it, MessageResponse::class.java)
                            sendCodeResultMessage =
                                if (!response.errorResponse.errorMessages.isNullOrEmpty()) {
                                    response.errorResponse.errorMessages[0]
                                } else {
                                    "입력값을 확인해주세요."
                                }
                        } catch (e: Exception) {
                            sendCodeResultMessage =
                                context.resources.getString(R.string.error_unspecified_message)
                            e.printStackTrace()
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

    fun checkEmailAuthCode(context: Context, email: String, code: String) {
        if (email.isNotEmpty()) {
            if (code.isNotEmpty()) {
                SignNetworkUtil.api.checkAuthCodeByEmail(email, code).runCatching {
                    val result = this.execute()
                    if (result.isSuccessful) {
                        result.body()?.let {
                            checkCodeMessage =
                                if (it.success) it.response?.message ?: "코드가 확인되었습니다."
                                else {
                                    if (it.errorResponse.errorMessages.isEmpty()) "이메일을 확인해주세요."
                                    else it.errorResponse.errorMessages[0]
                                }
                        }
                    } else {
                        result.errorBody()?.string()?.let {
                            try {
                                val response =
                                    Gson().fromJson(it, EmailCodeCheckResponse::class.java)
                                checkCodeMessage =
                                    if (response.errorResponse.errorMessages.isNotEmpty()) {
                                        response.errorResponse.errorMessages[0]
                                    } else {
                                        "입력값을 확인해주세요."
                                    }
                            } catch (e: Exception) {
                                checkCodeMessage =
                                    context.resources.getString(R.string.error_unspecified_message)
                                e.printStackTrace()
                            }
                        }
                    }
                    isCheckCodeComplete.postValue(result.isSuccessful)
                }.onFailure {
                    checkCodeMessage =
                        context.resources.getString(R.string.error_unspecified_message)
                    it.printStackTrace()
                }
            } else {
                checkCodeMessage = ""
                oneButtonDialogShow(context, context.resources.getString(R.string.error_code_empty))
            }
        } else {
            checkCodeMessage = ""
            oneButtonDialogShow(context, context.resources.getString(R.string.error_email_empty))
        }
    }

    fun signUpByEmail(context: Context): Boolean {
        val mbtiClass = MBTI.getMbtiByString(mbti.value)
        if (mbtiClass != null) {
            SignNetworkUtil.api.signUpByEmail(
                email,
                password,
                mbtiClass.value,
                nickname
            ).runCatching {
                val result = this.execute()
                if (result.isSuccessful) {
                    result.body()?.let {
                        it.response?.accessToken?.let { token ->
                            SecureManager(context).setToken(token)
                        }
                    }
                } else {
                    result.errorBody()?.string()?.let {
                        try {
                            val response = Gson().fromJson(it, EmailLoginResponse::class.java)
                            signUpResultMessage =
                                if (response.errorResponse.errorMessages.isNotEmpty()) {
                                    response.errorResponse.errorMessages[0]
                                } else {
                                    "입력값을 확인해주세요."
                                }
                        } catch (e: Exception) {
                            signUpResultMessage =
                                context.resources.getString(R.string.error_unspecified_message)
                            e.printStackTrace()
                        }
                    }
                }
                return result.isSuccessful
            }.onFailure {
                signUpResultMessage =
                    context.resources.getString(R.string.error_unspecified_message)
                it.printStackTrace()
            }
        }
        return false
    }

    fun clear() {
        isSendCode.value = false
        isCheckCodeComplete.value = false
        isTermsAgree.value = false
        sendCodeResultMessage = ""
        signUpResultMessage = ""
        checkCodeMessage = ""
        duplicatedNicknameResultMessage = ""
    }

    fun messageClear() {
        sendCodeResultMessage = ""
        signUpResultMessage = ""
        checkCodeMessage = ""
        duplicatedNicknameResultMessage = ""
    }
}