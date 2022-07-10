package com.android.finder.screen.fragment.community

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.android.finder.databinding.FragmentCommunityBinding
import com.android.finder.screen.CommonFragment
import com.android.finder.R
import com.android.finder.component.RecyclerViewItemDeco
import com.android.finder.enumdata.CommunityOrderBy
import com.android.finder.screen.dialog.MBTISelectDialog
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
    private var isLoading : Boolean = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.listViewModel = communityViewModel
        sortUiChange()
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
                            dataLoading(false)
                        }
                    }
                }
            })
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun dataLoading(isRefresh: Boolean) {
        if(!isLoading) {
            isLoading = true
            if(isRefresh) communityViewModel.currentPage = 0
            CoroutineScope(Dispatchers.IO).launch {
                communityViewModel.getCommunityList()
                isLoading = false
            }
        }

    }
    override fun eventListenerSetting() {
        binding.postButton.setOnClickListener(this)
        binding.fastestSortButton.setOnClickListener(this)
        binding.mostCommentsButton.setOnClickListener(this)
        binding.selectMbtiButton.setOnClickListener(this)

        communityViewModel.orderBy.observe(viewLifecycleOwner) {
            sortUiChange()
        }
        communityViewModel.mbti.observe(viewLifecycleOwner) {
            binding.selectedMbtiTextView.text = it
            dataLoading(true)
        }
    }

    override fun onClick(v: View?) {
        when(v) {
            binding.postButton -> {
                navigate(MainFragmentDirections.actionMainFragmentToCommunityWriteFragment())
            }
            binding.fastestSortButton -> {
                communityViewModel.orderBy.value = CommunityOrderBy.CREATE_TIME
            }
            binding.mostCommentsButton -> {
                communityViewModel.orderBy.value = CommunityOrderBy.ANSWER_COUNT
            }
            binding.selectMbtiButton -> {
                context?.let {
                    MBTISelectDialog(it).apply {
                        selectEvent = { communityViewModel.mbti.postValue(getMBTI() ?: "전체") }
                        show()
                    }
                }
            }
        }
    }

    private fun sortUiChange() {
        when(communityViewModel.orderBy.value){
            CommunityOrderBy.CREATE_TIME -> {
                binding.mostCommentsDotView.setImageResource(R.drawable.ic_gray_dot)
                binding.mostCommentsTextView.setTextColorResource(R.color.gray3)
                binding.fastestDotView.setImageResource(R.drawable.ic_main_2_color_dot)
                binding.fastestTextView.setTextColorResource(R.color.main_color_2)
            }
            CommunityOrderBy.ANSWER_COUNT -> {
                binding.fastestDotView.setImageResource(R.drawable.ic_gray_dot)
                binding.fastestTextView.setTextColorResource(R.color.gray3)
                binding.mostCommentsDotView.setImageResource(R.drawable.ic_main_2_color_dot)
                binding.mostCommentsTextView.setTextColorResource(R.color.main_color_2)
            }
        }
        dataLoading(true)
    }
}