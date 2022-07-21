package com.finder.android.mbti.screen.fragment.login

import android.content.Intent
import android.view.View
import androidx.fragment.app.viewModels
import com.finder.android.mbti.R
import com.finder.android.mbti.databinding.FragmentEmailLoginBinding
import com.finder.android.mbti.oneButtonDialogShow
import com.finder.android.mbti.CommonFragment
import com.finder.android.mbti.screen.activity.MainActivity
import com.finder.android.mbti.util.SettingUtil
import com.finder.android.mbti.viewmodel.LoginViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EmailLoginFragment : CommonFragment<FragmentEmailLoginBinding>(R.layout.fragment_email_login),
    View.OnClickListener {

    private val loginViewModel: LoginViewModel by viewModels()

    override fun eventListenerSetting() {
        binding.actionBar.backButton.setOnClickListener(this)
        binding.moveToSignUpButton.setOnClickListener(this)
        binding.autoLoginCheckLayout.setOnClickListener(this)
        binding.loginButton.setOnClickListener(this)

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
            binding.loginButton -> {
                isLoading = true
                val email = binding.emailEditTextView.text.toString()
                val password = binding.passwordEditTextView.text.toString()
                if (email.isEmpty()) {
                    oneButtonDialogShow(context, resources.getString(R.string.error_email_empty))
                    isLoading = false
                    return
                }
                if (password.isEmpty()) {
                    oneButtonDialogShow(context, resources.getString(R.string.error_password_empty))
                    isLoading = false
                    return
                }
                CoroutineScope(Dispatchers.IO).launch {
                    context?.let {
                        if (loginViewModel.login(it, email, password)) {
                            activity?.let { activity ->
                                SettingUtil.setAutoLoginKey(activity, loginViewModel.isAutoLogin.value?:false)
                                val sendIntent = Intent(activity, MainActivity::class.java)
                                sendIntent.run {
                                    flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                    startActivity(this)
                                }
                                activity.finish()
                            }
                        } else {
                            oneButtonDialogShow(
                                it,
                                resources.getString(R.string.error_login),
                                loginViewModel.loginErrorMessage
                            )
                        }
                    }
                    isLoading = false
                }
            }
        }
    }
}