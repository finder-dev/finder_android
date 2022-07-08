package com.android.finder.bindingadapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.android.finder.R
import com.android.finder.enum.HomeBottomTap
import com.android.finder.screen.CommonFragment
import com.android.finder.screen.fragment.community.CommunityFragment
import com.android.finder.screen.fragment.home.HomeFragment
import com.android.finder.screen.fragment.my.MyFragment

class MainFragmentAdapter(
    fragmentManager: FragmentManager, lifecycle: Lifecycle,
    private val tabNameList: List<String>
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int = tabNameList.size

    override fun createFragment(position: Int): Fragment {
        return when (tabNameList[position]) {
            HomeBottomTap.Home.tapName -> HomeFragment()
            HomeBottomTap.DEBATE.tapName -> HomeFragment()
            HomeBottomTap.COMMUNITY.tapName -> CommunityFragment()
            HomeBottomTap.MY.tapName -> MyFragment()
            else -> HomeFragment()
        }
    }
}