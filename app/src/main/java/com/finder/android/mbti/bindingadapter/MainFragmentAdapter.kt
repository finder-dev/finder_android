package com.finder.android.mbti.bindingadapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.finder.android.mbti.enumdata.HomeBottomTap
import com.finder.android.mbti.screen.fragment.SaveFragment
import com.finder.android.mbti.screen.fragment.community.CommunityFragment
import com.finder.android.mbti.screen.fragment.debate.DebateFragment
import com.finder.android.mbti.screen.fragment.home.HomeFragment
import com.finder.android.mbti.screen.fragment.my.MyFragment

class MainFragmentAdapter(
    fragmentManager: FragmentManager, lifecycle: Lifecycle,
    private val tabNameList: List<String>
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int = tabNameList.size

    override fun createFragment(position: Int): Fragment {
        return when (tabNameList[position]) {
            HomeBottomTap.Home.tapName -> HomeFragment()
            HomeBottomTap.DEBATE.tapName -> DebateFragment()
            HomeBottomTap.COMMUNITY.tapName -> CommunityFragment()
            HomeBottomTap.SAVE.tapName -> SaveFragment()
            HomeBottomTap.MY.tapName -> MyFragment()
            else -> HomeFragment()
        }
    }
}