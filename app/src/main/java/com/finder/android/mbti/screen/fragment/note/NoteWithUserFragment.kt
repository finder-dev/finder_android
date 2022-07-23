package com.finder.android.mbti.screen.fragment.note

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.finder.android.mbti.*
import com.finder.android.mbti.caching.CachingData
import com.finder.android.mbti.component.RecyclerViewItemDeco
import com.finder.android.mbti.databinding.FragmentNoteWithUserBinding
import com.finder.android.mbti.result.StringResult
import com.finder.android.mbti.screen.dialog.SelectListBottomSheetDialog
import com.finder.android.mbti.screen.fragment.community.CommunityDetailFragmentDirections
import com.finder.android.mbti.viewmodel.NoteListWithUserViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class NoteWithUserFragment :
    CommonFragment<FragmentNoteWithUserBinding>(R.layout.fragment_note_with_user),
    View.OnClickListener {

    private val args: NoteWithUserFragmentArgs by navArgs()
    private val noteWithUserViewModel: NoteListWithUserViewModel by viewModels()
    private var isDatALoading = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (args.userData != null) {
            noteWithUserViewModel.targetUserData = args.userData
            binding.titleView.text = noteWithUserViewModel.targetUserData?.userNickname!!
        } else {
            toastShow(context, resources.getString(R.string.error_unspecified_message))
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

    private fun dataLoading(isRefresh: Boolean) {
        if(!isDatALoading) {
            isDatALoading = true
            if (isRefresh) noteWithUserViewModel.currentPage = 0
            CoroutineScope(Dispatchers.IO).launch {
                isLoading = true
                if (!noteWithUserViewModel.getNotes()) {
                    oneButtonDialogShow(
                        context,
                        resources.getString(R.string.error_load),
                        noteWithUserViewModel.getListResultMessage
                    ) { navPopStack() }
                }
                isLoading = false
                isDatALoading = false
            }
        }

    }

    override fun eventListenerSetting() {
        binding.backButton.setOnClickListener(this)
        binding.sendANoteButton.setOnClickListener(this)
        binding.attributeButton.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.backButton -> navPopStack()
            binding.sendANoteButton -> {
                args.userData?.let {
                    navigate(
                        NoteWithUserFragmentDirections.actionNoteWithUserFragmentToSendNoteFragment(
                            it.targetUserId
                        )
                    )
                }
            }
            binding.attributeButton -> {
                val target = noteWithUserViewModel.targetUserData
                if (target != null) {
                    val itemList = arrayListOf(
                        resources.getString(R.string.delete_all),
                        resources.getString(R.string.block),
                        resources.getString(R.string.report),
                        resources.getString(R.string.close)
                    )
                    val fragmentContext = context
                    val dialog = SelectListBottomSheetDialog(itemList).apply {
                        result = object : StringResult {
                            override fun finish(data: String) {
                                when (data) {
                                    resources.getString(R.string.delete_all) -> {
                                        twoButtonDialogShow(
                                            context,
                                            message = resources.getString(R.string.question_delete_all_note),
                                            subMessage = resources.getString(R.string.msg_delete_all_note_sub),
                                            closeButtonTitle = resources.getString(R.string.delete),
                                            confirmButtonTitle = resources.getString(R.string.cancel),
                                            closeEvent = {
                                                isLoading = true
                                                CoroutineScope(Dispatchers.IO).launch {
                                                    if (noteWithUserViewModel.deleteUserNote(target.targetUserId)) {
                                                        oneButtonDialogShow(
                                                            fragmentContext,
                                                            message = App.instance.resources.getString(R.string.delete_all_note_complete),
                                                            subMessage = noteWithUserViewModel.deleteUserNoteResultMessage
                                                        ) { navPopStack() }
                                                    } else {
                                                        oneButtonDialogShow(
                                                            fragmentContext,
                                                            message = App.instance.resources.getString(R.string.delete_all_note_failed),
                                                            subMessage = noteWithUserViewModel.deleteUserNoteResultMessage
                                                        )
                                                    }
                                                    isLoading = false
                                                }
                                            }
                                        )
                                    }
                                    resources.getString(R.string.block) -> {
                                        twoButtonDialogShow(
                                            context,
                                            message = resources.getString(R.string.question_user_block),
                                            subMessage = resources.getString(R.string.msg_block_sub),
                                            closeButtonTitle = resources.getString(R.string.block),
                                            confirmButtonTitle = resources.getString(R.string.cancel),
                                            closeEvent = {
                                                isLoading = true
                                                CoroutineScope(Dispatchers.IO).launch {
                                                    if (noteWithUserViewModel.blockUser(target.targetUserId)) {
                                                        oneButtonDialogShow(
                                                            fragmentContext,
                                                            message = App.instance.resources.getString(R.string.user_block_complete),
                                                            subMessage = noteWithUserViewModel.blockUserResultMessage
                                                        ) { navPopStack() }
                                                    } else {
                                                        oneButtonDialogShow(
                                                            fragmentContext,
                                                            message = App.instance.resources.getString(R.string.user_block_failed),
                                                            subMessage = noteWithUserViewModel.blockUserResultMessage
                                                        )
                                                    }
                                                    isLoading = false
                                                }
                                            }
                                        )
                                    }
                                    resources.getString(R.string.report) -> {
                                        twoButtonDialogShow(
                                            context,
                                            message = resources.getString(R.string.question_user_report),
                                            subMessage = resources.getString(R.string.msg_report_sub),
                                            closeButtonTitle = resources.getString(R.string.report),
                                            confirmButtonTitle = resources.getString(R.string.cancel),
                                            closeEvent = {
                                                isLoading = true
                                                CoroutineScope(Dispatchers.IO).launch {
                                                    if (noteWithUserViewModel.reportUser(target.targetUserId)) {
                                                        oneButtonDialogShow(
                                                            fragmentContext,
                                                            message = App.instance.resources.getString(R.string.user_report_complete),
                                                            subMessage = noteWithUserViewModel.reportUserResultMessage
                                                        ) { navPopStack() }
                                                    } else {
                                                        oneButtonDialogShow(
                                                            fragmentContext,
                                                            message = App.instance.resources.getString(R.string.user_report_failed),
                                                            subMessage = noteWithUserViewModel.reportUserResultMessage
                                                        )
                                                    }
                                                    isLoading = false
                                                }
                                            }
                                        )
                                    }
                                }
                                dismiss()
                            }

                        }
                    }
                    dialog.show(childFragmentManager, "attribute")
                }
            }
        }
    }
}