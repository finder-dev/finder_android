package com.finder.android.mbti.screen.fragment.note

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.finder.android.mbti.*
import com.finder.android.mbti.component.RecyclerViewItemDeco
import com.finder.android.mbti.databinding.FragmentNoteWithUserBinding
import com.finder.android.mbti.viewmodel.NoteListWithUserViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

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
        binding.userNoteViewModel = noteWithUserViewModel
        dataLoading(true)
        try {
            binding.noteRecyclerView.addItemDecoration(
                RecyclerViewItemDeco(requireContext(), 8)
            )
            binding.noteRecyclerView.addOnScrollListener(object :
                RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (scrollPercent(binding.noteRecyclerView) >= 90) {
                        if (!noteWithUserViewModel.isLast) {
                            dataLoading(false)
                        }
                    }
                }
            })
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun dataLoading(isRefresh:Boolean) {
        if(isRefresh) noteWithUserViewModel.currentPage = 0
        CoroutineScope(Dispatchers.IO).launch {
            if(!noteWithUserViewModel.getNotes()) {
                oneButtonDialogShow(
                    context,
                    resources.getString(R.string.error_load),
                    noteWithUserViewModel.getListResultMessage
                ) { navPopStack()}
            }
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