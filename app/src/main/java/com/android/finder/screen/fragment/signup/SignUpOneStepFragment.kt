package com.android.finder.screen.fragment.signup

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import com.android.finder.databinding.FragmentSignUpOneBinding
import com.android.finder.screen.CommonFragment
import com.android.finder.R
import com.android.finder.network.SignNetworkUtil
import com.android.finder.oneButtonDialogShow
import com.android.finder.viewmodel.SignUpViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignUpOneStepFragment: CommonFragment<FragmentSignUpOneBinding>(R.layout.fragment_sign_up_one) , View.OnClickListener {

    private val signUpViewModel : SignUpViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        signUpViewModel.message = ""
        binding.actionBar.titleView.text = resources.getString(R.string.sign_up)
    }
    override fun eventListenerSetting() {
        binding.actionBar.backButton.setOnClickListener(this)
        binding.nextButton.setOnClickListener(this)
        binding.authenticationRequestButton.setOnClickListener(this)

        signUpViewModel.isSendCode.observe(viewLifecycleOwner) {
            if(signUpViewModel.message.isNotEmpty()) {
                oneButtonDialogShow(context, signUpViewModel.message)
            }
        }
    }

    override fun onClick(button: View?) {
        when(button) {
            binding.actionBar.backButton -> navPopStack()
            binding.authenticationRequestButton -> {
                CoroutineScope(Dispatchers.IO).launch {
                    val email = binding.emailEditTextView.text.toString()
                    context?.let { signUpViewModel.sendEmailAuthCode(it, email) }
                }
            }
            binding.nextButton -> {
                navigate(SignUpOneStepFragmentDirections.actionSignUpOneStepFragmentToSignUpTwoStepFragment())
            }
        }
    }
}