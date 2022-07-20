package com.finder.android.mbti.screen.fragment.note

import android.view.View
import com.finder.android.mbti.R
import com.finder.android.mbti.databinding.FragmentSendANoteBinding
import com.finder.android.mbti.CommonFragment

class SendNoteFragment : CommonFragment<FragmentSendANoteBinding>(R.layout.fragment_send_a_note), View.OnClickListener {

    override fun eventListenerSetting() {
        binding.closeButton.setOnClickListener(this)
        binding.sendButton.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v) {
            binding.sendButton -> {

            }
            binding.closeButton -> navPopStack()
        }
    }

}