package com.android.finder.screen.fragment.login

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.FragmentManager
import com.android.finder.R
import com.android.finder.databinding.FragmentLoginBinding
import com.android.finder.screen.CommonFragment

class LoginFragment : CommonFragment<FragmentLoginBinding>(R.layout.fragment_login), View.OnClickListener {

    private lateinit var callback: OnBackPressedCallback

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                activity?.finish()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onDetach() {
        super.onDetach()
        callback.remove()
    }

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