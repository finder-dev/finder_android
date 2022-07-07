package com.android.finder.screen.fragment.signup

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import com.android.finder.databinding.FragmentSignUpTwoBinding
import com.android.finder.screen.CommonFragment
import com.android.finder.R
import com.android.finder.oneButtonDialogShow
import com.android.finder.screen.dialog.MBTISelectDialog
import com.android.finder.setTextColorResource
import com.android.finder.viewmodel.SignUpViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignUpTwoStepFragment :
    CommonFragment<FragmentSignUpTwoBinding>(R.layout.fragment_sign_up_two), View.OnClickListener,
    TextWatcher {

    private val signUpViewModel: SignUpViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.actionBar.titleView.text = resources.getString(R.string.sign_up)
    }

    override fun onClick(button: View?) {
        when (button) {
            binding.actionBar.backButton -> navPopStack()
            binding.selectMBTILayout -> {
                context?.let {
                    MBTISelectDialog(it).apply {
                        selectEvent = {
                            val mbti = this.getMBTI()
                            if (mbti != null) {
                                signUpViewModel.mbti = mbti
                                binding.mbtiDisplayView.setTextColorResource(R.color.black1)
                                binding.mbtiDisplayView.text = mbti
                            } else {
                                binding.mbtiDisplayView.setTextColorResource(R.color.light_gray)
                            }
                            binding.nextButton.isEnabled = isDataFull()
                        }
                        show()
                    }
                }
            }
            binding.nicknameDuplicationCheckButton -> {
                context?.let {
                    CoroutineScope(Dispatchers.IO).launch {
                        val nickname = binding.nicknameEditTextView.text.toString()
                        signUpViewModel.checkDuplicatedNickname(it, nickname)
                        oneButtonDialogShow(
                            it,
                            resources.getString(R.string.nickname_duplication),
                            signUpViewModel.duplicatedNicknameResultMessage
                        )
                    }
                }

            }
            binding.termsAgreeCheckLayout -> {
                signUpViewModel.isTermsAgree.value = !(signUpViewModel.isTermsAgree.value ?: false)
                binding.nextButton.isEnabled = isDataFull()
            }
            binding.nextButton -> {
                signUpViewModel.nickname = binding.nicknameEditTextView.text.toString()
                context?.let {
                    CoroutineScope(Dispatchers.IO).launch {
                        if (signUpViewModel.signUpByEmail(it)) {
                            navigate(SignUpTwoStepFragmentDirections.actionSignUpTwoStepFragmentToSignUpCompleteFragment())
                        } else {
                            oneButtonDialogShow(
                                it,
                                resources.getString(R.string.error_sign_up),
                                signUpViewModel.signUpResultMessage
                            )
                        }
                    }
                }
            }
        }
    }

    override fun eventListenerSetting() {
        binding.actionBar.backButton.setOnClickListener(this)
        binding.nextButton.setOnClickListener(this)
        binding.selectMBTILayout.setOnClickListener(this)
        binding.termsAgreeCheckLayout.setOnClickListener(this)
        binding.nicknameDuplicationCheckButton.setOnClickListener(this)

        binding.nicknameEditTextView.addTextChangedListener(this)


        signUpViewModel.isTermsAgree.observe(viewLifecycleOwner) {
            binding.termsAgreeCheckButton.setImageResource(if
                    (it) R.drawable.ic_check_on_button else R.drawable.ic_check_off_button)
        }
    }

    private fun isDataFull(): Boolean {
        val nickname = binding.nicknameEditTextView.text.toString()
        return signUpViewModel.mbti.isNotEmpty() && nickname.isNotEmpty() && signUpViewModel.isTermsAgree.value?:false
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        binding.nextButton.isEnabled = isDataFull()
    }

    override fun afterTextChanged(s: Editable?) {}
}