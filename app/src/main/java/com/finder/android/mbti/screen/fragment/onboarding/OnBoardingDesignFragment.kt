package com.finder.android.mbti.screen.fragment.onboarding

import android.os.Bundle
import android.view.View
import com.finder.android.mbti.CommonFragment
import com.finder.android.mbti.R
import com.finder.android.mbti.databinding.FragmentOnBoardingDesignBinding
import com.finder.android.mbti.util.SettingUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class OnBoardingDesignFragment(val position: Int) :
    CommonFragment<FragmentOnBoardingDesignBinding>(R.layout.fragment_on_boarding_design),
    View.OnClickListener {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.onBoardingImageView.setImageResource(when(position) {
            0 -> R.drawable.ic_on_boarding_one
            1 -> R.drawable.ic_on_boarding_two
            else -> R.drawable.ic_on_boarding_three
        })
        when(position) {
            0 -> {
                binding.onBoardingImageView.setImageResource(R.drawable.ic_on_boarding_one)
                binding.mainTextView.text = resources.getString(R.string.msg_one_on_boarding_main)
                binding.subTextView.text = resources.getString(R.string.msg_one_on_boarding_sub)
                binding.onBoardingDotImageView.setImageResource(R.drawable.ic_on_boarding_one_dot)
            }
            1 -> {
                binding.onBoardingImageView.setImageResource(R.drawable.ic_on_boarding_two)
                binding.mainTextView.text = resources.getString(R.string.msg_two_on_boarding_main)
                binding.subTextView.text = resources.getString(R.string.msg_two_on_boarding_sub)
                binding.onBoardingDotImageView.setImageResource(R.drawable.ic_on_boarding_two_dot)
            }
            else -> {
                binding.onBoardingImageView.setImageResource(R.drawable.ic_on_boarding_three)
                binding.mainTextView.text = resources.getString(R.string.msg_three_on_boarding_main)
                binding.subTextView.text = resources.getString(R.string.msg_three_on_boarding_sub)
                binding.onBoardingDotImageView.setImageResource(R.drawable.ic_on_boarding_three_dot)
            }
        }
        when(position) {
            2 -> {
                binding.skipButton.visibility = View.GONE
                binding.startButton.visibility = View.VISIBLE
            }
            else -> {
                binding.skipButton.visibility = View.VISIBLE
                binding.startButton.visibility = View.GONE
            }
        }
    }
    override fun eventListenerSetting() {
        binding.skipButton.setOnClickListener(this)
        binding.startButton.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        CoroutineScope(Dispatchers.IO).launch {
            context?.let { SettingUtil.setOnBoardingKey(it) }
        }
        //여긴 추후에 소셜로그인 적용시 다시 바꿔야함
        navigate(OnBoardingFragmentDirections.actionOnBoardingFragmentToEmailLoginFragment())
    }
}