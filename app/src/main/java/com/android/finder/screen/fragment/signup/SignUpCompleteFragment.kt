package com.android.finder.screen.fragment.signup

import android.view.View
import com.android.finder.databinding.FragmentSignUpCompleteBinding
import com.android.finder.screen.CommonFragment
import com.android.finder.R

class SignUpCompleteFragment: CommonFragment<FragmentSignUpCompleteBinding>(R.layout.fragment_sign_up_complete), View.OnClickListener {
    override fun eventListenerSetting() {
        binding.signUpCompleteButton.setOnClickListener(this)
    }

    override fun onClick(button: View?) {
        when(button) {
            binding.signUpCompleteButton -> {
                //메인 화면으로 이동?
            }
        }
    }
}