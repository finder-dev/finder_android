package com.finder.android.mbti.screen.fragment.home

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.viewModels
import com.finder.android.mbti.R
import com.finder.android.mbti.caching.CachingData
import com.finder.android.mbti.databinding.FragmentHomeBinding
import com.finder.android.mbti.component.RecyclerViewItemDeco
import com.finder.android.mbti.dpToPx
import com.finder.android.mbti.enumdata.MBTI
import com.finder.android.mbti.CommonFragment
import com.finder.android.mbti.screen.fragment.MainFragmentDirections
import com.finder.android.mbti.setTextColorResource
import com.finder.android.mbti.viewmodel.HomeViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeFragment : CommonFragment<FragmentHomeBinding>(R.layout.fragment_home),
    View.OnClickListener {

    private val homeViewModel: HomeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        refresh()
        binding.communityHomeView.homeViewModel = homeViewModel
        context?.let {
            binding.communityHomeView.communityHotRecyclerView.addItemDecoration(
                RecyclerViewItemDeco(it, 29)
            )
        }

        binding.balanceGameView.emptyIncludeView.descriptionTextView.text =
            resources.getString(R.string.msg_empty_balance_game_main)
        binding.communityHomeView.emptyIncludeView.descriptionTextView.text =
            resources.getString(R.string.msg_empty_community)
    }

    private fun refresh() {
        CoroutineScope(Dispatchers.IO).launch {
            context?.let { homeViewModel.getProfile(it) }
            homeViewModel.getHotList()
            homeViewModel.getDebateHot()
        }
    }

    override fun eventListenerSetting() {
        binding.moveToDebateButton.setOnClickListener(this)
        binding.moveToNoteView.setOnClickListener(this)

        homeViewModel.isExistProfile.observe(viewLifecycleOwner) {
            if (it) {
                val user = CachingData.userProfile
                user?.let { profile ->
                    binding.userIntroduceView.text = "${profile.mbti} ${profile.nickname}"
                    val mbti = MBTI.getMbtiByString(profile.mbti)
                    if(mbti != null) {
                        binding.characterImageView.setImageResource(mbti.mbtiImageResource)
                        binding.welcomeTextView.text = resources.getString(mbti.welcomeMentResource)
                    }
                }
            }
        }
        homeViewModel.hotDebate.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.balanceGameView.apply {
                    balanceGamePromotionLayout.visibility = View.VISIBLE
                    emptyIncludeView.root.visibility = View.GONE
                    balanceGameTitleView.text = it.title
                    remainingTimeView.text = it.deadline
                    gamePossibleCount.text = it.optionACount.toString()
                    balancePossibleButton.text = it.optionA
                    gameImpossibleCount.text = it.optionBCount.toString()
                    balanceImpossibleButton.text = it.optionB
                    binding.moveToDebateTextview.text = resources.getString(R.string.move_to_leave_a_comment)
                }
            } else {
                binding.moveToDebateTextview.text = resources.getString(R.string.move_to_debate_create)
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
            binding.moveToDebateButton -> {
                homeViewModel.hotDebate.value?.let {
                    navigate(MainFragmentDirections.actionMainFragmentToDebateDetailFragment(it.debateId))
                }
            }
            binding.moveToNoteView -> {
                navigate(MainFragmentDirections.actionMainFragmentToNoteListFragment())
            }
        }
    }

}