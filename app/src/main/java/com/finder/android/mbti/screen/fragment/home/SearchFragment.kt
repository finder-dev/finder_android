package com.finder.android.mbti.screen.fragment.home

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.finder.android.mbti.*
import com.finder.android.mbti.component.RecyclerViewItemDeco
import com.finder.android.mbti.database.DataBaseUtil
import com.finder.android.mbti.database.entity.SearchWordEntity
import com.finder.android.mbti.databinding.FragmentSearchBinding
import com.finder.android.mbti.enumdata.CommunityOrderBy
import com.finder.android.mbti.screen.fragment.MainFragmentDirections
import com.finder.android.mbti.viewmodel.SearchViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.lang.Exception

class SearchFragment : CommonFragment<FragmentSearchBinding>(R.layout.fragment_search), View.OnClickListener {

    private val searchViewModel : SearchViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.actionBar.titleView.text = resources.getString(R.string.search)
        binding.searchViewModel = searchViewModel
        binding.includeSearchWord.searchViewModel = searchViewModel
        binding.includeSearchCommunityList.searchViewModel = searchViewModel
        try {
            binding.mbtiSearchEditTextView.setText("")
            binding.includeSearchWord.searchWordRecyclerView.addItemDecoration(
                RecyclerViewItemDeco(requireContext(), 12)
            )
            binding.includeSearchCommunityList.searchCommunityRecyclerView.addItemDecoration(
                RecyclerViewItemDeco(requireContext(), 42)
            )
            binding.includeSearchCommunityList.searchCommunityRecyclerView.addOnScrollListener(object :
                RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (scrollPercent(binding.includeSearchCommunityList.searchCommunityRecyclerView) >= 90) {
                        if (!searchViewModel.isLast) {
                            dataLoading(false)
                        }
                    }
                }
            })
        } catch (e: Exception) {
            e.printStackTrace()
        }
        getSearchDataLoading()
        binding.includeSearchCommunityList.emptyIncludeView.descriptionTextView.text = resources.getString(R.string.msg_empty_search_result)
        upKeyboard()
    }

    override fun onResume() {
        super.onResume()
        if (!EventBus.getDefault().isRegistered(this)) EventBus.getDefault().register(this)
    }

    override fun onPause() {
        super.onPause()
        if (EventBus.getDefault().isRegistered(this)) EventBus.getDefault().unregister(this)
    }

    private fun dataLoading(isRefresh : Boolean) {
        val searchWord = binding.mbtiSearchEditTextView.text.toString()
        if(searchWord.isNotEmpty()) {
            binding.mbtiSearchEditTextView.clearFocus()
            if(isRefresh) searchViewModel.currentPage = 0
            isLoading = true
            CoroutineScope(Dispatchers.IO).launch {
                DataBaseUtil.addWord(searchWord)
                searchViewModel.getSearchList(searchWord)
                isLoading = false
            }
        }
    }



    private fun getSearchDataLoading() {
        isLoading = true
        CoroutineScope(Dispatchers.IO).launch{
            searchViewModel.getSearchWord()
            isLoading = false
        }
    }

    override fun eventListenerSetting() {
        binding.actionBar.backButton.setOnClickListener(this)
        binding.searchIcon.setOnClickListener(this)
        binding.includeSearchWord.allDeleteWordButton.setOnClickListener(this)
        binding.includeSearchCommunityList.fastestSortButton.setOnClickListener(this)
        binding.includeSearchCommunityList.mostCommentsButton.setOnClickListener(this)

        searchViewModel.orderBy.observe(viewLifecycleOwner) {
            sortUiChange()
        }

        binding.mbtiSearchEditTextView.setOnFocusChangeListener { _, hasFocus ->
            if(hasFocus) {
                CoroutineScope(Dispatchers.IO).launch { searchViewModel.getSearchWord() }
                binding.includeSearchCommunityList.root.visibility = View.GONE
                binding.includeSearchWord.root.visibility = View.VISIBLE
            } else {
                binding.includeSearchCommunityList.root.visibility = View.VISIBLE
                binding.includeSearchWord.root.visibility = View.GONE
            }
        }

        binding.mbtiSearchEditTextView.setOnKeyListener { _, keyCode, event ->
            if ((event.action== KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                dataLoading(isRefresh = true)
                return@setOnKeyListener true
            } else {
                return@setOnKeyListener false
            }
        }
    }

    private fun upKeyboard() {
        activity?.also { activity ->
            val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
            binding.mbtiSearchEditTextView.requestFocus()
        }
    }

    override fun onClick(v: View?) {
        when(v) {
            binding.actionBar.backButton -> navPopStack()
            binding.searchIcon -> dataLoading(isRefresh = true)
            binding.includeSearchCommunityList.fastestSortButton -> {
                searchViewModel.orderBy.value = CommunityOrderBy.CREATE_TIME
            }
            binding.includeSearchCommunityList.mostCommentsButton -> {
                searchViewModel.orderBy.value = CommunityOrderBy.ANSWER_COUNT
            }
            binding.includeSearchWord.allDeleteWordButton -> {
                CoroutineScope(Dispatchers.IO).launch {
                    DataBaseUtil.deleteAllWord()
                    searchViewModel.getSearchWord()
                }
            }
        }
    }

    private fun sortUiChange() {
        when (searchViewModel.orderBy.value) {
            CommunityOrderBy.CREATE_TIME -> {
                binding.includeSearchCommunityList.apply {
                    mostCommentsDotView.setImageResource(R.drawable.ic_gray_dot)
                    mostCommentsTextView.setTextColorResource(R.color.gray3)
                    fastestDotView.setImageResource(R.drawable.ic_main_2_color_dot)
                    fastestTextView.setTextColorResource(R.color.black1)
                }
            }
            CommunityOrderBy.ANSWER_COUNT -> {
                binding.includeSearchCommunityList.apply {
                    fastestDotView.setImageResource(R.drawable.ic_gray_dot)
                    fastestTextView.setTextColorResource(R.color.gray3)
                    mostCommentsDotView.setImageResource(R.drawable.ic_main_2_color_dot)
                    mostCommentsTextView.setTextColorResource(R.color.black1)
                }
            }
        }
        dataLoading(true)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun searchWordClick(event: ClickSearchWordEvent) {
        binding.mbtiSearchEditTextView.setText(event.word.searchWord)
        dataLoading(true)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun contentIsLike(event: LikeCommunityContent) {
        searchViewModel.contentList.find { it.communityId == event.content.communityId }?.let {
            CoroutineScope(Dispatchers.IO).launch {
                if (searchViewModel.likeChange(it.communityId)) {
                    it.likeUser = !it.likeUser
                    CoroutineScope(Dispatchers.Main).launch {
                        try {
                            if (it.likeUser) {
                                it.likeCount++
                                toastShow(context, resources.getString(R.string.msg_like_success))
                            } else {
                                it.likeCount--
                                toastShow(context,  resources.getString(R.string.msg_like_delete))
                            }
                            binding.includeSearchCommunityList.searchCommunityRecyclerView.adapter?.notifyItemChanged(event.position)
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                } else {
                    toastShow(context,  resources.getString(R.string.error_unspecified_message))
                }
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun moveCommunityDetail(event: MoveToCommunityDetail) {
        navigate(SearchFragmentDirections.actionSearchFragmentToCommunityDetailFragment(event.communityId))
    }
}