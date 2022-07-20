package com.finder.android.mbti.screen.fragment.note

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.finder.android.mbti.CommonFragment
import com.finder.android.mbti.MoveToNoteWithAnotherUser
import com.finder.android.mbti.R
import com.finder.android.mbti.databinding.FragmentNoteListBinding
import com.finder.android.mbti.scrollPercent
import com.finder.android.mbti.viewmodel.NoteViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class NoteListFragment: CommonFragment<FragmentNoteListBinding>(R.layout.fragment_note_list),View.OnClickListener {

    private val noteViewModel :NoteViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.actionBar.titleView.text = resources.getString(R.string.note_box)
        binding.emptyIncludeView.descriptionTextView.text =resources.getString(R.string.msg_empty_note)

        binding.noteViewModel = noteViewModel

        dataLoading(true)
        binding.noteTitleRecyclerView.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (scrollPercent(binding.noteTitleRecyclerView) >= 90) {
                    if (!noteViewModel.isLast) {
                        dataLoading(false)
                    }
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()
        if (!EventBus.getDefault().isRegistered(this)) EventBus.getDefault().register(this)
    }

    override fun onPause() {
        super.onPause()
        if (EventBus.getDefault().isRegistered(this)) EventBus.getDefault().unregister(this)
    }


    override fun eventListenerSetting() {
        binding.actionBar.backButton.setOnClickListener(this)
    }

    fun dataLoading(isRefresh : Boolean) {
        if(isRefresh) noteViewModel.currentPage = 0
        CoroutineScope(Dispatchers.IO).launch {
            noteViewModel.getAllNoteList()
        }
    }

    override fun onClick(v: View?) {
        when(v) {
            binding.actionBar.backButton -> navPopStack()
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun moveToNoteListWithAnotherUser(event : MoveToNoteWithAnotherUser) {
        navigate(NoteListFragmentDirections.actionNoteListFragmentToNoteWithUserFragment(event.data))
    }
}