package com.android.finder.screen.fragment.note

import android.view.View
import com.android.finder.R
import com.android.finder.databinding.FragmentSendANoteBinding
import com.android.finder.screen.CommonFragment

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