package com.android.finder.screen.fragment.login

import android.view.View
import com.android.finder.databinding.FragmentLoginBinding
import com.android.finder.screen.CommonFragment
import com.android.finder.R

class LoginFragment : CommonFragment<FragmentLoginBinding>(R.layout.fragment_login), View.OnClickListener {
    override fun eventListenerSetting() {
        binding.startToEmailButton.setOnClickListener(this)
    }

    override fun onClick(button: View?) {
        when(button) {
            binding.startToEmailButton -> {
                navigate(LoginFragmentDirections.actionLoginFragmentToEmailLoginFragment())
            }
        }
    }

}