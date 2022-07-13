package com.android.finder.screen.fragment.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.android.finder.MoveToCommunityDetail
import com.android.finder.databinding.FragmentHomeBinding
import com.android.finder.screen.CommonFragment
import com.android.finder.R
import com.android.finder.caching.CachingData
import com.android.finder.component.RecyclerViewItemDeco
import com.android.finder.screen.fragment.MainFragmentDirections
import com.android.finder.viewmodel.HomeViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class HomeFragment : CommonFragment<FragmentHomeBinding>(R.layout.fragment_home) , View.OnClickListener {

    private val homeViewModel : HomeViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        refresh()
        binding.debateView.homeViewModel = homeViewModel
        context?.let {
            binding.debateView.communityHotRecyclerView.addItemDecoration(
                RecyclerViewItemDeco(it, 29)
            )
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

    private fun refresh() {
        CoroutineScope(Dispatchers.IO).launch {
            context?.let { homeViewModel.getProfile(it) }
            homeViewModel.getHotList()
        }
    }
    override fun eventListenerSetting() {
        homeViewModel.isExistProfile.observe(viewLifecycleOwner) {
            if(it) {
                val user = CachingData.userProfile
                user?.let { profile ->
                    binding.userIntroduceView.text ="${profile.mbti} ${profile.nickname}"
                }
            }
        }
    }

    override fun onClick(p0: View?) {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun moveCommunityDetail(event : MoveToCommunityDetail) {
        navigate(MainFragmentDirections.actionMainFragmentToCommunityDetailFragment(event.communityId))
    }

}