package com.android.finder.screen.fragment.login

import android.content.Intent
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import com.android.finder.R
import com.android.finder.databinding.FragmentEmailLoginBinding
import com.android.finder.oneButtonDialogShow
import com.android.finder.screen.CommonFragment
import com.android.finder.screen.activity.MainActivity
import com.android.finder.viewmodel.LoginViewModel
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
                val email = binding.emailEditTextView.text.toString()
                val password = binding.passwordEditTextView.text.toString()
                if (email.isEmpty()) {
                    oneButtonDialogShow(context, resources.getString(R.string.error_email_empty))
                    return
                }
                if (password.isEmpty()) {
                    oneButtonDialogShow(context, resources.getString(R.string.error_password_empty))
                    return
                }
                Log.e("email",email)
                Log.e("password", password)
                CoroutineScope(Dispatchers.IO).launch {
                    context?.let {
                        if (loginViewModel.login(it, email, password)) {
                            activity?.let { activity ->
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
                }
            }
        }
    }
}