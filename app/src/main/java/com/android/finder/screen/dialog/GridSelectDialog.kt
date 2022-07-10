package com.android.finder.screen.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.android.finder.R
import com.android.finder.component.RecyclerViewGridItemDeco
import com.android.finder.databinding.DialogGridSelectBinding
import com.android.finder.dataobj.TextListItem
import com.android.finder.enumdata.MBTI
import com.android.finder.list.griddialog.GridSelectDialogItemAdapter

class GridSelectDialog(context : Context, val itemList : List<String>) : Dialog(context), View.OnClickListener {

    var selectEvent : () -> Unit = {}
    var listAdapter : GridSelectDialogItemAdapter? = null

    private val binding by lazy {
        DataBindingUtil.inflate<DialogGridSelectBinding>(
            layoutInflater,
            R.layout.dialog_grid_select,
            null,
            false
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setCancelable(false)
        binding.confirmButton.setOnClickListener(this)
        binding.selectItemRecyclerView.apply {
            layoutManager = GridLayoutManager(context, 2)
            val list = ArrayList<TextListItem>().apply {
                itemList.forEach { this.add(TextListItem(it, false)) }
            }
            listAdapter = GridSelectDialogItemAdapter(context, list)
            adapter = listAdapter
            addItemDecoration(RecyclerViewGridItemDeco(
                context,
                16,
                16,
                10,
                10,
                2
            ))
        }
    }

    fun getItem() : String? {
        listAdapter?.let {
            return it.currentSelectItem
        }?:return null
    }

    override fun onClick(p0: View?) {
        when(p0) {
            binding.confirmButton -> {
                selectEvent()
                dismiss()
            }
        }
    }
}