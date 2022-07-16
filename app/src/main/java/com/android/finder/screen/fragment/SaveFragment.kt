package com.android.finder.screen.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.android.finder.MoveToCommunityDetail
import com.android.finder.R
import com.android.finder.component.RecyclerViewItemDeco
import com.android.finder.databinding.FragmentSaveBinding
import com.android.finder.screen.CommonFragment
import com.android.finder.scrollPercent
import com.android.finder.viewmodel.SaveViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class SaveFragment : CommonFragment<FragmentSaveBinding>(R.layout.fragment_save) {

    private val saveViewModel : SaveViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.listViewModel = saveViewModel
        dataLoading(true)
        context?.let {
            binding.saveContentRecyclerView.addItemDecoration(
                RecyclerViewItemDeco(requireContext(), 42)
            )
            binding.saveContentRecyclerView.addOnScrollListener(object :
                RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (scrollPercent(binding.saveContentRecyclerView) >= 90) {
                        if (!saveViewModel.isLast) {
                            dataLoading(false)
                        }
                    }
                }
            })
        }
    }

    override fun eventListenerSetting() {}

    private fun dataLoading(isRefresh : Boolean) {
        if (!isLoading) {
            isLoading = true
            if (isRefresh) saveViewModel.currentPage = 0
            CoroutineScope(Dispatchers.IO).launch {
                saveViewModel.getSaveCommunityList()
                isLoading = false
            }
        }
    }
}