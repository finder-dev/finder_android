package com.android.finder.screen.fragment.login

import android.view.View
import androidx.fragment.app.viewModels
import com.android.finder.R
import com.android.finder.databinding.FragmentEmailLoginBinding
import com.android.finder.screen.CommonFragment
import com.android.finder.viewmodel.LoginViewModel

class EmailLoginFragment : CommonFragment<FragmentEmailLoginBinding>(R.layout.fragment_email_login),
    View.OnClickListener {

    private val loginViewModel: LoginViewModel by viewModels()

    override fun eventListenerSetting() {
        binding.actionBar.backButton.setOnClickListener(this)
        binding.moveToSignUpButton.setOnClickListener(this)
        binding.autoLoginCheckLayout.setOnClickListener(this)

        loginViewModel.isAutoLogin.observe(viewLifecycleOwner) {
            binding.autoLoginCheckButton.setImageResource(if (it) R.drawable.ic_check_on_button else R.drawable.ic_check_off_button)
        }
    }

    override fun onClick(button: View?) {
        when (button) {
            binding.actionBar.backButton -> navPopStack()
            binding.moveToSignUpButton -> navigate(EmailLoginFragmentDirections.actionEmailLoginFragmentToSignUpOneStepFragment())
            binding.autoLoginCheckLayout -> {
                loginViewModel.isAutoLogin.postValue(!(loginViewModel.isAutoLogin.value ?: false))
            }
        }
    }
}