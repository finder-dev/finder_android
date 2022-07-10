package com.android.finder.screen.fragment.community

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.android.finder.databinding.FragmentCommunityBinding
import com.android.finder.screen.CommonFragment
import com.android.finder.R
import com.android.finder.component.RecyclerViewItemDeco
import com.android.finder.enum.CommunityOrderBy
import com.android.finder.screen.fragment.MainFragmentDirections
import com.android.finder.scrollPercent
import com.android.finder.setTextColorResource
import com.android.finder.viewmodel.CommunityViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class CommunityFragment : CommonFragment<FragmentCommunityBinding>(R.layout.fragment_community), View.OnClickListener {

    private val communityViewModel : CommunityViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.listViewModel = communityViewModel
        refresh()
        try {
            binding.communityRecyclerView.addItemDecoration(RecyclerViewItemDeco(
                requireContext(),
                42
            ))
            binding.communityRecyclerView.addOnScrollListener(object :
                RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (scrollPercent(binding.communityRecyclerView) >= 100) {
                        if (communityViewModel.currentPage <= communityViewModel.lastPage) {
                            dataLoading()
                        }
                    }
                }
            })
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun refresh() {
        communityViewModel.currentPage = 0
        dataLoading()
    }

    fun dataLoading() {
        CoroutineScope(Dispatchers.IO).launch {
            communityViewModel.getCommunityList()
        }
    }
    override fun eventListenerSetting() {
        binding.postButton.setOnClickListener(this)
        binding.fastestSortButton.setOnClickListener(this)
        binding.mostCommentsButton.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v) {
            binding.postButton -> {
                navigate(MainFragmentDirections.actionMainFragmentToCommunityWriteFragment())
            }
            binding.fastestSortButton -> {
                binding.mostCommentsDotView.setImageResource(R.drawable.ic_gray_dot)
                binding.mostCommentsTextView.setTextColorResource(R.color.gray3)
                binding.fastestDotView.setImageResource(R.drawable.ic_main_2_color_dot)
                binding.fastestTextView.setTextColorResource(R.color.main_color_2)
                communityViewModel.orderBy = CommunityOrderBy.CREATE_TIME
                refresh()
            }
            binding.mostCommentsButton -> {
                binding.fastestDotView.setImageResource(R.drawable.ic_gray_dot)
                binding.fastestTextView.setTextColorResource(R.color.gray3)
                binding.mostCommentsDotView.setImageResource(R.drawable.ic_main_2_color_dot)
                binding.mostCommentsTextView.setTextColorResource(R.color.main_color_2)
                communityViewModel.orderBy = CommunityOrderBy.ANSWER_COUNT
                refresh()
            }
        }
    }
}