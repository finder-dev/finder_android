package com.finder.android.mbti.screen.fragment.signup

import android.content.Intent
import android.view.View
import com.finder.android.mbti.databinding.FragmentSignUpCompleteBinding
import com.finder.android.mbti.CommonFragment
import com.finder.android.mbti.R
import com.finder.android.mbti.screen.activity.MainActivity

class SignUpCompleteFragment: CommonFragment<FragmentSignUpCompleteBinding>(R.layout.fragment_sign_up_complete), View.OnClickListener {
    override fun eventListenerSetting() {
        binding.signUpCompleteButton.setOnClickListener(this)
    }

    override fun onClick(button: View?) {
        when(button) {
            binding.signUpCompleteButton -> {
                activity?.let {
                    val sendIntent = Intent(it, MainActivity::class.java)
                    sendIntent.run {
                        flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(this)
                    }
                    it.finish()
                }
            }
        }
    }
}