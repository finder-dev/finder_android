package com.android.finder.screen.fragment.home

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.compose.ui.unit.Constraints
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.viewModels
import com.android.finder.MoveToCommunityDetail
import com.android.finder.databinding.FragmentHomeBinding
import com.android.finder.screen.CommonFragment
import com.android.finder.R
import com.android.finder.caching.CachingData
import com.android.finder.component.RecyclerViewItemDeco
import com.android.finder.dpToPx
import com.android.finder.screen.fragment.MainFragmentDirections
import com.android.finder.setTextColorResource
import com.android.finder.viewmodel.HomeViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class HomeFragment : CommonFragment<FragmentHomeBinding>(R.layout.fragment_home),
    View.OnClickListener {

    private val homeViewModel: HomeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        refresh()
        binding.debateView.homeViewModel = homeViewModel
        context?.let {
            binding.debateView.communityHotRecyclerView.addItemDecoration(
                RecyclerViewItemDeco(it, 29)
            )
        }

        binding.balanceGameView.emptyIncludeView.descriptionTextView.text =
            resources.getString(R.string.msg_empty_balance_game_main)
    }

    override fun onResume() {
        super.onResume()
        if (!EventBus.getDefault().isRegistered(this)) EventBus.getDefault().register(this)
    }

    override fun onPause() {
        super.onPause()
        if (EventBus.getDefault().isRegistered(this)) EventBus.getDefault().unregister(this)
    }

    private fun refresh() {
        CoroutineScope(Dispatchers.IO).launch {
            context?.let { homeViewModel.getProfile(it) }
            homeViewModel.getHotList()
            homeViewModel.getDebateHot()
        }
    }

    override fun eventListenerSetting() {
        binding.balanceGameView.moveToDebateButton.setOnClickListener(this)

        homeViewModel.isExistProfile.observe(viewLifecycleOwner) {
            if (it) {
                val user = CachingData.userProfile
                user?.let { profile ->
                    binding.userIntroduceView.text = "${profile.mbti} ${profile.nickname}"
                }
            }
        }
        homeViewModel.hotDebate.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.balanceGameView.apply {
                    emptyIncludeView.root.visibility = View.GONE
                    balanceGameTitleView.text = it.title
                    remainingTimeView.text = it.deadline
                    gamePossibleCount.text = it.optionACount.toString()
                    balancePossibleButton.text = it.optionA
                    gameImpossibleCount.text = it.optionBCount.toString()
                    balanceImpossibleButton.text = it.optionB
                    moveToDebateTextview.text = resources.getString(R.string.move_to_leave_a_comment)
                }
            } else {
                binding.balanceGameView.moveToDebateTextview.text = resources.getString(R.string.move_to_debate_create)
                binding.balanceGameView.emptyIncludeView.root.visibility = View.VISIBLE
                binding.balanceGameView.balanceGamePromotionLayout.visibility = View.GONE
            }
        }

        homeViewModel.isDebateJoin.observe(viewLifecycleOwner) {
            balanceGameOnOff(it, homeViewModel.isA)
        }
    }

    private fun balanceGameOnOff(join: Boolean, isA: Boolean) {
        if(join) {
            balanceGameOptionAOnOff(isA)
            balanceGameOptionBOnOff(!isA)
        } else {
            balanceGameOptionAOnOff(false)
            balanceGameOptionBOnOff(false)
        }
    }

    private fun balanceGameOptionAOnOff(
        isOn: Boolean
    ) {
        val onCountParams = ConstraintLayout.LayoutParams(
            ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT
        )
        onCountParams.topToTop = ConstraintLayout.LayoutParams.PARENT_ID
        onCountParams.rightToRight = ConstraintLayout.LayoutParams.PARENT_ID
        val offCountParams = ConstraintLayout.LayoutParams(
            ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT
        )
        offCountParams.topToTop = ConstraintLayout.LayoutParams.PARENT_ID
        offCountParams.rightToRight = ConstraintLayout.LayoutParams.PARENT_ID
        context?.let {
            onCountParams.rightMargin = 12.dpToPx(it)
            offCountParams.leftMargin = 12.dpToPx(it)
            offCountParams.topMargin = 20.dpToPx(it)
        }
        binding.balanceGameView.apply {
            if(isOn) {
                possibleCutImageView.visibility = View.VISIBLE
                gamePossibleCount.layoutParams = onCountParams
                gamePossibleCount.setTextColorResource(R.color.navy)
                balancePossibleButton.setBackgroundColor(resources.getColor(R.color.navy, null))
                balancePossibleButton.setTextColorResource(R.color.white)
            } else {
                possibleCutImageView.visibility = View.INVISIBLE
                gamePossibleCount.layoutParams = offCountParams
                gamePossibleCount.setTextColorResource(R.color.gray3)
                balancePossibleButton.setBackgroundColor(resources.getColor(R.color.white, null))
                balancePossibleButton.setTextColorResource(R.color.gray3)
            }
        }
    }

    private fun balanceGameOptionBOnOff(
        isOn: Boolean
    ) {
        val onCountParams = ConstraintLayout.LayoutParams(
            ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT
        )
        onCountParams.topToTop = ConstraintLayout.LayoutParams.PARENT_ID
        onCountParams.leftToLeft = ConstraintLayout.LayoutParams.PARENT_ID
        val offCountParams = ConstraintLayout.LayoutParams(
            ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT
        )
        offCountParams.topToTop = ConstraintLayout.LayoutParams.PARENT_ID
        offCountParams.leftToLeft = ConstraintLayout.LayoutParams.PARENT_ID
        context?.let {
            onCountParams.rightMargin = 12.dpToPx(it)
            offCountParams.leftMargin = 12.dpToPx(it)
            offCountParams.topMargin = 20.dpToPx(it)
        }
        binding.balanceGameView.apply {
            if(isOn) {
                impossibleCutImageView.visibility = View.VISIBLE
                gameImpossibleCount.layoutParams = onCountParams
                gameImpossibleCount.setTextColorResource(R.color.navy)
                balanceImpossibleButton.setBackgroundColor(resources.getColor(R.color.navy, null))
                balanceImpossibleButton.setTextColorResource(R.color.white)
            } else {
                impossibleCutImageView.visibility = View.INVISIBLE
                gameImpossibleCount.layoutParams = offCountParams
                gameImpossibleCount.setTextColorResource(R.color.gray3)
                balanceImpossibleButton.setBackgroundColor(resources.getColor(R.color.white, null))
                balanceImpossibleButton.setTextColorResource(R.color.gray3)
            }
        }
    }

    override fun onClick(v: View?) {
        when(v) {
            binding.balanceGameView.moveToDebateButton -> {
                //토론 상세로 넘어가기
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun moveCommunityDetail(event: MoveToCommunityDetail) {
        navigate(MainFragmentDirections.actionMainFragmentToCommunityDetailFragment(event.communityId))
    }

}