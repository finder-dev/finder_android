package com.finder.android.mbti.screen.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.finder.android.mbti.R
import com.finder.android.mbti.component.RecyclerViewItemDeco
import com.finder.android.mbti.databinding.FragmentSaveBinding
import com.finder.android.mbti.CommonFragment
import com.finder.android.mbti.scrollPercent
import com.finder.android.mbti.viewmodel.SaveViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SaveFragment : CommonFragment<FragmentSaveBinding>(R.layout.fragment_save) {

    private val saveViewModel : SaveViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.listViewModel = saveViewModel
        binding.emptyIncludeView.descriptionTextView.text = resources.getString(R.string.msg_no_save_content)
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