package com.android.finder.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel: ViewModel() {

    var isAutoLogin : MutableLiveData<Boolean> = MutableLiveData(true)
}