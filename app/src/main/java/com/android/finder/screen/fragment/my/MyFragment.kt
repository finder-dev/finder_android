package com.android.finder.screen.fragment.my

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.android.finder.*
import com.android.finder.databinding.FragmentMyBinding
import com.android.finder.screen.CommonFragment
import com.android.finder.caching.CachingData
import com.android.finder.component.RecyclerViewItemDeco
import com.android.finder.enumdata.MBTI
import com.android.finder.network.MainNetWorkUtil
import com.android.finder.screen.activity.SignActivity
import com.android.finder.screen.fragment.MainFragmentDirections
import com.android.finder.util.SecureManager
import com.android.finder.util.SettingUtil
import com.android.finder.viewmodel.MyViewModel
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.lang.Exception

class MyFragment : CommonFragment<FragmentMyBinding>(R.layout.fragment_my), View.OnClickListener {

    private val myViewModel : MyViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.myViewModel = myViewModel
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText(resources.getString(R.string.my_write_content)))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText(resources.getString(R.string.my_write_comment)))

        binding.emptyIncludeView.descriptionTextView.text = if(binding.tabLayout.selectedTabPosition == 1) {
            resources.getString(R.string.error_empty_my_comment_content)
        } else {
            resources.getString(R.string.error_empty_my_content)
        }
        context?.let {
            myViewModel.getProfile(it)
            binding.myJoinContentRecyclerView.addItemDecoration(
                RecyclerViewItemDeco(it, 42)
            )
        }
        binding.myJoinContentRecyclerView.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (scrollPercent(binding.myJoinContentRecyclerView) >= 90) {
                        if (!myViewModel.isLast) {
                            dataLoading(false, binding.tabLayout.selectedTabPosition == 1)
                        }
                    }
                }
            })
        dataLoading(false, binding.tabLayout.selectedTabPosition == 1)
    }

    override fun onResume() {
        super.onResume()
        if (!EventBus.getDefault().isRegistered(this)) EventBus.getDefault().register(this)
    }

    override fun onPause() {
        super.onPause()
        if (EventBus.getDefault().isRegistered(this)) EventBus.getDefault().unregister(this)
    }

    private fun dataLoading(isRefresh : Boolean, isComment : Boolean) {
        if(!isLoading) {
            isLoading = true
            if(isRefresh) myViewModel.currentPage = 0
            CoroutineScope(Dispatchers.IO).launch {
                if(!isComment) {
                    myViewModel.getMyCommunityList()
                } else {
                    myViewModel.getMyCommunityListThroughComment()
                }
                isLoading = false
            }
        }

    }

    override fun eventListenerSetting() {
        binding.settingButton.setOnClickListener(this)
        binding.logoutButton.setOnClickListener(this)

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                binding.emptyIncludeView.descriptionTextView.text = if(tab?.position == 1) {
                    resources.getString(R.string.error_empty_my_comment_content)
                } else {
                    resources.getString(R.string.error_empty_my_content)
                }
                dataLoading(true, tab?.position == 1)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabReselected(tab: TabLayout.Tab?) {}

        }
        )
        myViewModel.isExistProfile.observe(viewLifecycleOwner) {
            if (it) {
                val user = CachingData.userProfile
                user?.let { profile ->
                    binding.userNicknameView.text = profile.nickname
                    binding.userEmailView.text = profile.email
                }
            }
        }
    }

    override fun onClick(v: View?) {
        when(v) {
            binding.settingButton -> {
                navigate(MainFragmentDirections.actionMainFragmentToSettingFragment())
            }
            binding.logoutButton -> {
                twoButtonDialogShow(
                    context,
                    message = resources.getString(R.string.question_logout),
                    subMessage = null,
                    closeButtonTitle = resources.getString(R.string.logout),
                    confirmButtonTitle = resources.getString(R.string.no),
                    closeEvent = {
                        CoroutineScope(Dispatchers.IO).launch {
                            val result = myViewModel.logout()
                            if(result) {
                                CoroutineScope(Dispatchers.Main).launch {
                                    context?.let {
                                        SettingUtil.setAutoLoginKey(it, false)
                                        SecureManager(it).removeToken()
                                    }
                                    val sendIntent = Intent(context, SignActivity::class.java)
                                    sendIntent.run {
                                        flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                        startActivity(this)
                                    }
                                    activity?.finish()
                                }
                            } else {
                                oneButtonDialogShow(
                                    context,
                                    resources.getString(R.string.error_unspecified_message)
                                )
                            }
                        }
                    }
                )
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun contentIsLike(event: LikeCommunityContent) {
        myViewModel.contentList.find { it.communityId == event.content.communityId }?.let {
            CoroutineScope(Dispatchers.IO).launch {
                if (myViewModel.likeChange(it.communityId)) {
                    it.likeUser = !it.likeUser
                    CoroutineScope(Dispatchers.Main).launch {
                        try {
                            if (it.likeUser) {
                                it.likeCount++
                                toastShow(context, resources.getString(R.string.msg_like_success))
                            } else {
                                it.likeCount--
                                toastShow(context,  resources.getString(R.string.msg_like_delete))
                            }
                            binding.myJoinContentRecyclerView.adapter?.notifyItemChanged(event.position)
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                } else {
                    toastShow(context,  resources.getString(R.string.error_unspecified_message))
                }
            }
        }
    }
}