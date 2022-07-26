package com.finder.android.mbti.screen.fragment.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.finder.android.mbti.CommonFragment
import com.finder.android.mbti.R
import com.finder.android.mbti.databinding.FragmentSearchBinding
import com.finder.android.mbti.viewmodel.SearchViewModel

class SearchFragment : CommonFragment<FragmentSearchBinding>(R.layout.fragment_search), View.OnClickListener {

    private val searchViewModel : SearchViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.actionBar.titleView.text = resources.getString(R.string.search)
    }
    override fun eventListenerSetting() {
        binding.actionBar.backButton.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v) {
            binding.actionBar.backButton -> navPopStack()
        }
    }
}