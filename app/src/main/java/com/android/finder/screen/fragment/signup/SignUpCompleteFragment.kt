package com.android.finder.screen.fragment.signup

import android.content.Intent
import android.view.View
import com.android.finder.databinding.FragmentSignUpCompleteBinding
import com.android.finder.screen.CommonFragment
import com.android.finder.R
import com.android.finder.screen.activity.MainActivity

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