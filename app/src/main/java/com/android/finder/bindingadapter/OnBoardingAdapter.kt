package com.android.finder.bindingadapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.android.finder.screen.fragment.onboarding.OnBoardingDesignFragment

class OnBoardingAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 3
    override fun createFragment(position: Int): Fragment {
        return OnBoardingDesignFragment(position)
    }


}