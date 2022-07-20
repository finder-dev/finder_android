package com.finder.android.mbti.screen.fragment.note

import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.finder.android.mbti.R
import com.finder.android.mbti.databinding.FragmentSendANoteBinding
import com.finder.android.mbti.CommonFragment
import com.finder.android.mbti.oneButtonDialogShow
import com.finder.android.mbti.viewmodel.SendMessageViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SendNoteFragment : CommonFragment<FragmentSendANoteBinding>(R.layout.fragment_send_a_note),
    View.OnClickListener {

    private val messageViewModel: SendMessageViewModel by viewModels()
    private val args: SendNoteFragmentArgs by navArgs()

    override fun eventListenerSetting() {
        binding.closeButton.setOnClickListener(this)
        binding.sendButton.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.sendButton -> {
                val content = binding.noteContentEditTextView.text.toString()
                if (content.isNotEmpty()) {
                    isLoading = true
                    CoroutineScope(Dispatchers.IO).launch {
                        val result = messageViewModel.sendANote(args.userId, content)
                        oneButtonDialogShow(
                            context,
                            if (result) resources.getString(R.string.success_send_note)
                            else resources.getString(R.string.error_send_note),
                            messageViewModel.sendResultMessage
                        ) {
                            navPopStack()
                        }
                        isLoading = false
                    }
                }
            }
            binding.closeButton -> navPopStack()
        }
    }

}