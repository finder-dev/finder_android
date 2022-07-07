package com.android.finder.screen.fragment.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.android.finder.databinding.FragmentHomeBinding
import com.android.finder.screen.CommonFragment
import com.android.finder.R
import com.android.finder.caching.CachingData
import com.android.finder.viewmodel.HomeViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeFragment : CommonFragment<FragmentHomeBinding>(R.layout.fragment_home) , View.OnClickListener {

    private val homeViewModel : HomeViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setProfile()
    }

    private fun setProfile() {
        CoroutineScope(Dispatchers.IO).launch {
            context?.let {
                homeViewModel.getProfile(it)
            }
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

}