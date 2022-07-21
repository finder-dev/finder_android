package com.finder.android.mbti.screen.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.finder.android.mbti.R
import com.finder.android.mbti.component.RecyclerViewItemDeco
import com.finder.android.mbti.databinding.DialogDetailItemBinding
import com.finder.android.mbti.list.selectdialog.CommunityItemAdapter
import com.finder.android.mbti.result.StringResult
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SelectListBottomSheetDialog(val list: ArrayList<String>) :
    BottomSheetDialogFragment() {

    private var _binding: DialogDetailItemBinding? = null
    val binding get() = _binding!!
    var result : StringResult? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.dialog_detail_item,
            null,
            false
        )
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        context?.let { context ->
            result?.let {
                binding.dialogItemRecyclerView.adapter = CommunityItemAdapter(context, list, it)
            }
            binding.dialogItemRecyclerView.addItemDecoration(
                RecyclerViewItemDeco(context, 32)
            )
        }

    }
}