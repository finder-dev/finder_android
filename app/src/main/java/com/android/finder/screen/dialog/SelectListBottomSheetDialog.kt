package com.android.finder.screen.dialog

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.android.finder.R
import com.android.finder.component.RecyclerViewItemDeco
import com.android.finder.databinding.DialogDetailItemBinding
import com.android.finder.list.selectdialog.CommunityItemAdapter
import com.android.finder.result.StringResult
import com.google.android.material.bottomsheet.BottomSheetDialog
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
        Log.e("dialog", "onCreateView")
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.e("dialog", "onCreate")
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