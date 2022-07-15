package com.android.finder.screen.fragment.debate

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.android.finder.R
import com.android.finder.component.RecyclerViewItemDeco
import com.android.finder.databinding.FragmentDebateBinding
import com.android.finder.screen.CommonFragment
import com.android.finder.scrollPercent
import com.android.finder.viewmodel.DebateViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DebateFragment: CommonFragment<FragmentDebateBinding>(R.layout.fragment_debate), View.OnClickListener {

    private val debateViewModel : DebateViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.debateViewModel = debateViewModel
        dataLoading(true)
        context?.let {
            binding.debateRecyclerView.addItemDecoration(
                RecyclerViewItemDeco(it, 10)
            )
            binding.debateRecyclerView.addOnScrollListener(object :
                RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (scrollPercent(binding.debateRecyclerView) >= 90) {
                        if (!debateViewModel.isLast) {
                            dataLoading(false)
                        }
                    }
                }
            })
        }
    }

    private fun dataLoading(isRefresh: Boolean) {
        if (!isLoading) {
            isLoading = true
            if (isRefresh) debateViewModel.currentPage = 0
            CoroutineScope(Dispatchers.IO).launch {
                debateViewModel.getDebateListFromServer()
                isLoading = false
            }
        }
    }

    override fun eventListenerSetting() {
        binding.addDebateButton.setOnClickListener(this)
        binding.listFilterButton.setOnClickListener(this)

        debateViewModel.currentFilter.observe(viewLifecycleOwner) {
            binding.currentFilterTextView.text = it.filterString
            dataLoading(true)
        }
    }

    override fun onClick(v: View?) {
        when(v) {
            binding.addDebateButton -> {

            }
            binding.listFilterButton -> {

            }
        }
    }
}