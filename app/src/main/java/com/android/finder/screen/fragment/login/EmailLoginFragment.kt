package com.android.finder.screen.fragment.login

import android.view.View
import com.android.finder.R
import com.android.finder.databinding.FragmentEmailLoginBinding
import com.android.finder.screen.CommonFragment

class EmailLoginFragment : CommonFragment<FragmentEmailLoginBinding>(R.layout.fragment_email_login), View.OnClickListener {
    override fun eventListenerSetting() {
        binding.actionBar.backButton.setOnClickListener(this)
    }

    override fun onClick(button: View?) {
        when(button) {
            binding.actionBar.backButton -> {
                navPopStack()
            }
        }
    }
}