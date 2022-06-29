package com.android.finder.screen.fragment.signup

import android.os.Bundle
import android.view.View
import com.android.finder.databinding.FragmentSignUpTwoBinding
import com.android.finder.screen.CommonFragment
import com.android.finder.R

class SignUpTwoStepFragment: CommonFragment<FragmentSignUpTwoBinding>(R.layout.fragment_sign_up_two), View.OnClickListener {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.actionBar.titleView.text = resources.getString(R.string.sign_up)
    }
    override fun onClick(button: View?) {
        when(button) {
            binding.actionBar.backButton -> navPopStack()
            binding.nextButton -> {
                navigate(SignUpTwoStepFragmentDirections.actionSignUpTwoStepFragmentToSignUpCompleteFragment())
            }
        }
    }

    override fun eventListenerSetting() {
        binding.actionBar.backButton.setOnClickListener(this)
        binding.nextButton.setOnClickListener(this)
    }
}