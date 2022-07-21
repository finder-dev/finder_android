package com.finder.android.mbti.screen.fragment.signup

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.fragment.app.activityViewModels
import com.finder.android.mbti.databinding.FragmentSignUpTwoBinding
import com.finder.android.mbti.CommonFragment
import com.finder.android.mbti.R
import com.finder.android.mbti.oneButtonDialogShow
import com.finder.android.mbti.screen.dialog.MBTISelectDialog
import com.finder.android.mbti.setTextColorResource
import com.finder.android.mbti.viewmodel.SignUpViewModel
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
                            signUpViewModel.mbti.value = mbti
                            binding.nextButton.isEnabled = isDataFull()
                        }
                        show()
                    }
                }
            }
            binding.moveToTermsButton -> {
                navigate(
                    SignUpTwoStepFragmentDirections.actionSignUpTwoStepFragmentToWebFragment(
                    "https://pineapple-session-93c.notion.site/513cc9a19e4f40c491b43fa025340898"
                ))
            }
            binding.termsAgreeCheckButton -> {
                signUpViewModel.isTermsAgree.value = !(signUpViewModel.isTermsAgree.value ?: false)
                binding.nextButton.isEnabled = isDataFull()
            }
            binding.nextButton -> {
                signUpViewModel.nickname = binding.nicknameEditTextView.text.toString()
                context?.let {
                    isLoading = true
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
                        isLoading = false
                    }
                }
            }
        }
    }

    private fun setMbtiText(mbti : String?) {
        if(!mbti.isNullOrEmpty()) {
            binding.mbtiDisplayView.setTextColorResource(R.color.black1)
            binding.mbtiDisplayView.text = mbti
        } else {
            binding.mbtiDisplayView.setTextColorResource(R.color.light_gray)
            binding.mbtiDisplayView.text = resources.getString(R.string.hint_MBTI_select)
        }

    }

    override fun eventListenerSetting() {
        binding.actionBar.backButton.setOnClickListener(this)
        binding.nextButton.setOnClickListener(this)
        binding.selectMBTILayout.setOnClickListener(this)
        binding.termsAgreeCheckButton.setOnClickListener(this)
        binding.moveToTermsButton.setOnClickListener(this)

        binding.nicknameEditTextView.addTextChangedListener(this)

        signUpViewModel.mbti.observe(viewLifecycleOwner) {
            setMbtiText(it)
        }
        signUpViewModel.isTermsAgree.observe(viewLifecycleOwner) {
            binding.termsAgreeCheckButton.setImageResource(if
                    (it) R.drawable.ic_check_on_button else R.drawable.ic_check_off_button)
        }
    }

    private fun isDataFull(): Boolean {
        val nickname = binding.nicknameEditTextView.text.toString()
        return !signUpViewModel.mbti.value.isNullOrEmpty() && nickname.isNotEmpty() && signUpViewModel.isTermsAgree.value?:false
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        binding.nextButton.isEnabled = isDataFull()
    }

    override fun afterTextChanged(s: Editable?) {}
}