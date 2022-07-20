package com.finder.android.mbti.screen.fragment.onboarding

import android.os.Bundle
import android.view.View
import com.finder.android.mbti.R
import com.finder.android.mbti.bindingadapter.OnBoardingAdapter
import com.finder.android.mbti.databinding.FragmentOnBoardingBinding
import com.finder.android.mbti.CommonFragment

class OnBoardingFragment: CommonFragment<FragmentOnBoardingBinding>(R.layout.fragment_on_boarding) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.onBoardingPager.adapter = OnBoardingAdapter(this)
    }

    override fun eventListenerSetting() { }
}