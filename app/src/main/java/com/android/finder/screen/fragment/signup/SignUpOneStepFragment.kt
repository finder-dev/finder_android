package com.android.finder.screen.fragment.signup

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.fragment.app.activityViewModels
import com.android.finder.databinding.FragmentSignUpOneBinding
import com.android.finder.screen.CommonFragment
import com.android.finder.R
import com.android.finder.isValidEmail
import com.android.finder.oneButtonDialogShow
import com.android.finder.viewmodel.SignUpViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignUpOneStepFragment :
    CommonFragment<FragmentSignUpOneBinding>(R.layout.fragment_sign_up_one), View.OnClickListener,
    TextWatcher {

    private val signUpViewModel: SignUpViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        signUpViewModel.sendCodeResultMessage = ""
        binding.actionBar.titleView.text = resources.getString(R.string.sign_up)
    }

    override fun eventListenerSetting() {
        binding.actionBar.backButton.setOnClickListener(this)
        binding.nextButton.setOnClickListener(this)
        binding.authenticationRequestButton.setOnClickListener(this)

        binding.emailEditTextView.addTextChangedListener(this)
        binding.passwordConfirmEditTextView.addTextChangedListener(this)
        binding.passwordEditTextView.addTextChangedListener(this)
        binding.codeNumberEditTextView.addTextChangedListener(this)

        signUpViewModel.isSendCode.observe(viewLifecycleOwner) {
            if (signUpViewModel.sendCodeResultMessage.isNotEmpty()) {
                oneButtonDialogShow(context, signUpViewModel.sendCodeResultMessage)
            }
        }
    }

    override fun onClick(button: View?) {
        when (button) {
            binding.actionBar.backButton -> navPopStack()
            binding.authenticationRequestButton -> {
                CoroutineScope(Dispatchers.IO).launch {
                    val email = binding.emailEditTextView.text.toString()
                    context?.let { signUpViewModel.sendEmailAuthCode(it, email) }
                }
            }
            binding.nextButton -> {
                if (checkCondition()) {
                    signUpViewModel.apply {
                        email = binding.emailEditTextView.text.toString()
                        emailAuthCode = binding.codeNumberEditTextView.text.toString()
                        password = binding.passwordEditTextView.text.toString()
                    }
                    navigate(SignUpOneStepFragmentDirections.actionSignUpOneStepFragmentToSignUpTwoStepFragment())
                }
            }
        }
    }

    private fun checkCondition(): Boolean {
        val email = binding.emailEditTextView.text.toString()
        if (!isValidEmail(email)) {
            oneButtonDialogShow(context, resources.getString(R.string.error_email_check) , resources.getString(R.string.error_email_check_sub))
            return false
        }
        val password = binding.passwordEditTextView.text.toString()
        val confirmPassword = binding.passwordConfirmEditTextView.text.toString()
        if (confirmPassword != password) {
            oneButtonDialogShow(
                context,
                resources.getString(R.string.error_not_equal_password),
                resources.getString(R.string.error_not_equal_password_sub)
            )
            return false
        }
        return true
    }

    private fun isDataFull(): Boolean {
        val email = binding.emailEditTextView.text.toString()
        val emailCode = binding.codeNumberEditTextView.text.toString()
        val password = binding.passwordEditTextView.text.toString()
        val confirmPassword = binding.passwordConfirmEditTextView.text.toString()
        return email.isNotEmpty() && emailCode.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty()
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        binding.nextButton.isEnabled = isDataFull()
    }

    override fun afterTextChanged(s: Editable?) {}
}