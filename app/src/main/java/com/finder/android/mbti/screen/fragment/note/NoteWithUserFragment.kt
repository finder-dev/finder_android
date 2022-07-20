package com.finder.android.mbti.screen.fragment.note

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.finder.android.mbti.CommonFragment
import com.finder.android.mbti.R
import com.finder.android.mbti.databinding.FragmentNoteWithUserBinding
import com.finder.android.mbti.toastShow
import com.finder.android.mbti.viewmodel.NoteListWithUserViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteWithUserFragment : CommonFragment<FragmentNoteWithUserBinding>(R.layout.fragment_note_with_user), View.OnClickListener {

    private val args : NoteWithUserFragmentArgs by navArgs()
    private val noteWithUserViewModel : NoteListWithUserViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(args.userData != null) {
            noteWithUserViewModel.targetUserData = args.userData
            binding.titleView.text = noteWithUserViewModel.targetUserData?.userNickname!!
        } else {
            toastShow(context , resources.getString(R.string.error_unspecified_message))
            navPopStack()
        }
        dataLoading(true)
    }

    fun dataLoading(isRefresh:Boolean) {
        if(isRefresh) noteWithUserViewModel.currentPage = 0
        CoroutineScope(Dispatchers.IO).launch {
            noteWithUserViewModel.getNotes()
        }
    }
    override fun eventListenerSetting() {
        binding.backButton.setOnClickListener(this)
        binding.sendANoteButton.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v) {
            binding.backButton -> navPopStack()
            binding.sendANoteButton -> {
                args.userData?.let {
                    navigate(NoteWithUserFragmentDirections.actionNoteWithUserFragmentToSendNoteFragment(it.targetUserId))
                }

            }
        }
    }
}