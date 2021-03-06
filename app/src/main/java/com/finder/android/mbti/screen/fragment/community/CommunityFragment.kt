package com.finder.android.mbti.screen.fragment.community

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.finder.android.*
import com.finder.android.mbti.databinding.FragmentCommunityBinding
import com.finder.android.mbti.*
import com.finder.android.mbti.component.RecyclerViewItemDeco
import com.finder.android.mbti.enumdata.CommunityOrderBy
import com.finder.android.mbti.enumdata.MBTI
import com.finder.android.mbti.screen.dialog.GridSelectDialog
import com.finder.android.mbti.screen.fragment.MainFragmentDirections
import com.finder.android.mbti.viewmodel.CommunityViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.lang.Exception

class CommunityFragment : CommonFragment<FragmentCommunityBinding>(R.layout.fragment_community),
    View.OnClickListener {

    private val communityViewModel: CommunityViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.listViewModel = communityViewModel
        binding.emptyIncludeView.descriptionTextView.text =
            resources.getString(R.string.msg_empty_community)
        sortUiChange()
        try {
            binding.communityRecyclerView.addItemDecoration(
                RecyclerViewItemDeco(requireContext(), 42)
            )
            binding.communityRecyclerView.addOnScrollListener(object :
                RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (scrollPercent(binding.communityRecyclerView) >= 90) {
                        if (!communityViewModel.isLast) {
                            dataLoading(false)
                        }
                    }
                }
            })
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onResume() {
        super.onResume()
        if (!EventBus.getDefault().isRegistered(this)) EventBus.getDefault().register(this)
    }

    override fun onPause() {
        super.onPause()
        if (EventBus.getDefault().isRegistered(this)) EventBus.getDefault().unregister(this)
    }

    fun dataLoading(isRefresh: Boolean) {
        if (!isLoading) {
            isLoading = true
            if (isRefresh) communityViewModel.currentPage = 0
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
        when (v) {
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
                    GridSelectDialog(it, MBTI.getAllMbti(true)).apply {
                        selectEvent = { communityViewModel.mbti.postValue(getItem() ?: "??????") }
                        show()
                    }
                }
            }
        }
    }

    private fun sortUiChange() {
        when (communityViewModel.orderBy.value) {
            CommunityOrderBy.CREATE_TIME -> {
                binding.mostCommentsDotView.setImageResource(R.drawable.ic_gray_dot)
                binding.mostCommentsTextView.setTextColorResource(R.color.gray3)
                binding.fastestDotView.setImageResource(R.drawable.ic_main_2_color_dot)
                binding.fastestTextView.setTextColorResource(R.color.black1)
            }
            CommunityOrderBy.ANSWER_COUNT -> {
                binding.fastestDotView.setImageResource(R.drawable.ic_gray_dot)
                binding.fastestTextView.setTextColorResource(R.color.gray3)
                binding.mostCommentsDotView.setImageResource(R.drawable.ic_main_2_color_dot)
                binding.mostCommentsTextView.setTextColorResource(R.color.black1)
            }
        }
        dataLoading(true)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun contentIsLike(event: LikeCommunityContent) {
        communityViewModel.contentList.find { it.communityId == event.content.communityId }?.let {
            CoroutineScope(Dispatchers.IO).launch {
                if (communityViewModel.likeChange(it.communityId)) {
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
                            binding.communityRecyclerView.adapter?.notifyItemChanged(event.position)
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
}