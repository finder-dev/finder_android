package com.finder.android.mbti.screen.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.databinding.DataBindingUtil
import com.finder.android.mbti.databinding.FragmentMainBinding
import com.finder.android.mbti.R
import com.finder.android.mbti.bindingadapter.MainFragmentAdapter
import com.finder.android.mbti.databinding.ItemTabListBinding
import com.finder.android.mbti.CommonFragment
import com.finder.android.mbti.MoveToCommunityDetail
import com.finder.android.mbti.enumdata.HomeBottomTap
import com.finder.android.mbti.imageSrcCompatResource
import com.google.android.material.tabs.TabLayoutMediator
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class MainFragment : CommonFragment<FragmentMainBinding>(R.layout.fragment_main) {
    private var tabNameIdList = HomeBottomTap.values().map { it.tapName }
    private var tabIconIdList = HomeBottomTap.values().map { it.iconResourceId }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pageSetting()
    }

    override fun onResume() {
        super.onResume()
        if (!EventBus.getDefault().isRegistered(this)) EventBus.getDefault().register(this)
    }

    override fun onPause() {
        super.onPause()
        if (EventBus.getDefault().isRegistered(this)) EventBus.getDefault().unregister(this)
    }

    override fun onDestroyView() {
        binding.fragmentBodyPager.adapter = null
        super.onDestroyView()
    }

    private fun pageSetting() {
        binding.fragmentBodyPager.adapter = MainFragmentAdapter(childFragmentManager, viewLifecycleOwner.lifecycle, tabNameIdList)
        binding.fragmentBodyPager.isUserInputEnabled = false
        TabLayoutMediator(binding.tabLayout, binding.fragmentBodyPager) { tab, position ->
            DataBindingUtil.bind<ItemTabListBinding>(LayoutInflater.from(requireContext()).inflate(R.layout.item_tab_list, null))?.apply {
                this.title.text = tabNameIdList[position]
                this.title.setTextColor(resources.getColorStateList(R.color.tc_main_color_or_gray3, null))
                this.icon.imageSrcCompatResource(tabIconIdList[position])
            }?.run {
                tab.customView = this.root
            }
        }.attach()
    }
    override fun eventListenerSetting() {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun moveCommunityDetail(event: MoveToCommunityDetail) {
        navigate(MainFragmentDirections.actionMainFragmentToCommunityDetailFragment(event.communityId))
    }
}