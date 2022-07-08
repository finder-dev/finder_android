package com.android.finder.screen.fragment.my

import android.view.View
import com.android.finder.databinding.FragmentMyBinding
import com.android.finder.screen.CommonFragment
import com.android.finder.R

class MyFragment : CommonFragment<FragmentMyBinding>(R.layout.fragment_my), View.OnClickListener {

    override fun eventListenerSetting() {
        binding.settingButton.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v) {
            binding.settingButton -> {

            }
        }
    }
}