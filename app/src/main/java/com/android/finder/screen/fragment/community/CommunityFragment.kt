package com.android.finder.screen.fragment.community

import android.view.View
import com.android.finder.databinding.FragmentCommunityBinding
import com.android.finder.screen.CommonFragment
import com.android.finder.R
import com.android.finder.screen.fragment.MainFragmentDirections

class CommunityFragment : CommonFragment<FragmentCommunityBinding>(R.layout.fragment_community), View.OnClickListener {
    override fun eventListenerSetting() {
        binding.postButton.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v) {
            binding.postButton -> {
                navigate(MainFragmentDirections.actionMainFragmentToCommunityWriteFragment())
            }
        }
    }
}