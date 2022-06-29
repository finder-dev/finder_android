package com.android.finder.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.finder.network.SignNetworkUtil
import com.android.finder.oneButtonDialogShow

class SignUpViewModel : ViewModel() {

    var isSendCode : MutableLiveData<Boolean> = MutableLiveData(false)
    var message = ""

    fun sendEmailAuthCode(context : Context, email : String) {
        if(email.isNotEmpty()) {
            SignNetworkUtil.api.getAuthCodeByEmail(email).execute().runCatching {
                if(this.isSuccessful) {
                    this.body()?.let {
                        message = if(it.success) it.response.message?:"코드가 전송되었습니다."
                        else {
                            if(it.errorResponse.errorMessages.isEmpty()) "이메일을 확인해주세요."
                            else it.errorResponse.errorMessages[0]
                        }
                    }
                }
                isSendCode.postValue(this.isSuccessful)
            }
        } else {
            message = ""
            oneButtonDialogShow(context, "email을 입력해주세요.")
        }
    }
}