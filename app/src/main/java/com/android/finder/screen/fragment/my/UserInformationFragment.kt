package com.android.finder.screen.fragment.my

import android.os.Bundle
import android.view.View
import com.android.finder.R
import com.android.finder.databinding.FragmentUserInformationBinding
import com.android.finder.screen.CommonFragment

class UserInformationFragment: CommonFragment<FragmentUserInformationBinding>(R.layout.fragment_user_information), View.OnClickListener {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
    override fun eventListenerSetting() {
        binding.backButton.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v) {
            binding.backButton -> navPopStack()
        }
    }
}