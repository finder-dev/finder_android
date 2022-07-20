package com.finder.android.mbti.screen.fragment.my

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.finder.android.mbti.R
import com.finder.android.mbti.caching.CachingData
import com.finder.android.mbti.databinding.FragmentUserInformationBinding
import com.finder.android.mbti.oneButtonDialogShow
import com.finder.android.mbti.CommonFragment
import com.finder.android.mbti.screen.dialog.MBTISelectDialog
import com.finder.android.mbti.setTextColorResource
import com.finder.android.mbti.viewmodel.UserInformationViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserInformationFragment:
    CommonFragment<FragmentUserInformationBinding>(R.layout.fragment_user_information),
    View.OnClickListener,
    TextWatcher {

    private var isModifying = false
    private val userInformationViewModel : UserInformationViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUserInfo()
    }

    fun initUserInfo() {
        CoroutineScope(Dispatchers.IO).launch {
            context?.let {
                userInformationViewModel.getProfile(it)
            }
        }
    }
    override fun eventListenerSetting() {
        binding.backButton.setOnClickListener(this)
        binding.selectMBTILayout.setOnClickListener(this)
        binding.nickNameOverlapCheckButton.setOnClickListener(this)
        binding.modifyButton.setOnClickListener(this)

        binding.passwordConfirmEditTextView.addTextChangedListener(this)
        binding.passwordEditTextView.addTextChangedListener(this)

        userInformationViewModel.isExistProfile.observe(viewLifecycleOwner) {
            val user = CachingData.userProfile
            if(it && user != null) {
                binding.nicknameEditTextView.setText(user.nickname)
                userInformationViewModel.mbti = user.mbti
                binding.mbtiDisplayView.text = user.mbti
                binding.mbtiDisplayView.setTextColorResource(R.color.black1)
            }
        }
    }

    override fun onClick(v: View?) {
        when(v) {
            binding.backButton -> navPopStack()
            binding.nickNameOverlapCheckButton -> {
                isLoading = true
                CoroutineScope(Dispatchers.IO).launch {
                    val nickname = binding.nicknameEditTextView.text.toString()
                    val mainMessage = if(userInformationViewModel.checkNickname(nickname)) {
                        resources.getString(R.string.success_nickname_check)
                    } else {
                        resources.getString(R.string.error_nickName_duplicate)
                    }
                    oneButtonDialogShow(
                        context,
                        mainMessage,
                        userInformationViewModel.checkNicknameResultMessage
                    )
                    isLoading = false
                }
            }
            binding.modifyButton -> {
                if(!isModifying) {
                    isModifying = true
                    val nickname = binding.nicknameEditTextView.text.toString()
                    val mbti = userInformationViewModel.mbti
                    val password = binding.passwordEditTextView.text.toString()
                    val passwordConfirm = binding.passwordConfirmEditTextView.text.toString()
                    if(password != passwordConfirm) {
                        oneButtonDialogShow(
                            context,
                            resources.getString(R.string.error_password_length_check),
                            resources.getString(R.string.error_not_equal_password_sub)
                        )
                        isModifying = false
                        return
                    }
                    isLoading = true
                    CoroutineScope(Dispatchers.IO).launch {
                        var successEvent = {  }
                        val mainMessage = if(userInformationViewModel.modifyUserInformation(
                                nickname, mbti, password.ifEmpty { null }
                            )) {
                            successEvent = {
                                CoroutineScope(Dispatchers.IO).launch {
                                    context?.let {
                                        userInformationViewModel.initProfile(it)
                                    }
                                }
                                navPopStack()
                            }
                            resources.getString(R.string.success_modify_privacy)
                        } else {
                            resources.getString(R.string.failed_modify_privacy)
                        }
                        oneButtonDialogShow(
                            context,
                            mainMessage,
                            userInformationViewModel.modifyUserInfoResultMessage,
                            successEvent
                        )
                        isLoading = false
                        isModifying = false
                    }
                }
            }
            binding.selectMBTILayout -> {
                context?.let {
                    MBTISelectDialog(it).apply {
                        selectEvent = {
                            val mbti = this.getMBTI()
                            if (mbti != null) {
                                userInformationViewModel.mbti = mbti
                                binding.mbtiDisplayView.setTextColorResource(R.color.black1)
                                binding.mbtiDisplayView.text = mbti
                            }
                        }
                        show()
                    }
                }
            }
        }
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        val password = binding.passwordEditTextView.text.toString()
        val passwordConfirm = binding.passwordConfirmEditTextView.text.toString()
        binding.passwordCorrespondSuccessDescription.isVisible = password.isNotEmpty() && (password == passwordConfirm)
    }

    override fun afterTextChanged(p0: Editable?) { }
}