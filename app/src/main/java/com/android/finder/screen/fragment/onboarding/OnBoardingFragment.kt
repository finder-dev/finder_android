package com.android.finder.screen.fragment.onboarding

import android.os.Bundle
import android.view.View
import com.android.finder.R
import com.android.finder.bindingadapter.OnBoardingAdapter
import com.android.finder.databinding.FragmentOnBoardingBinding
import com.android.finder.screen.CommonFragment

class OnBoardingFragment: CommonFragment<FragmentOnBoardingBinding>(R.layout.fragment_on_boarding) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.onBoardingPager.adapter = OnBoardingAdapter(this)
    }

    override fun eventListenerSetting() { }
}