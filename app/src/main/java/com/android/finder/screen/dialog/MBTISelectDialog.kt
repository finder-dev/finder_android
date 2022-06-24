package com.android.finder.screen.dialog

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.View
import android.widget.RadioGroup
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import com.android.finder.R
import com.android.finder.databinding.DialogSelectMbtiBinding

class MBTISelectDialog(context: Context) : AlertDialog(context), View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    private val binding by lazy {
        DataBindingUtil.inflate<DialogSelectMbtiBinding>(
            layoutInflater,
            R.layout.dialog_one_button,
            null,
            false
        )
    }

    var selectEvent : () -> Unit = {}
    var M : String = ""
    var B : String = ""
    var T : String = ""
    var I : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setCancelable(false)
        binding.confirmButton.setOnClickListener(this)
    }


    override fun onClick(p0: View?) {
        when(p0) {
            binding.confirmButton -> {
                selectEvent()
                dismiss()
            }
        }
    }

    private fun getMBTI() : String? {
        if(M.isNotEmpty() && B.isNotEmpty() && T.isNotEmpty() && I.isNotEmpty()) {
            return "$M$B$T$I"
        } else return null
    }

    override fun onCheckedChanged(rg: RadioGroup?, checkedId: Int) {
        when(checkedId) {
            binding.selectEButton.id -> M = "E"
            binding.selectIButton.id -> M = "I"
            binding.selectSButton.id -> B = "S"
            binding.selectNButton.id -> B = "N"
            binding.selectTButton.id -> T = "T"
            binding.selectFButton.id -> T = "F"
            binding.selectPButton.id -> I = "P"
            binding.selectJButton.id -> I = "J"
        }
    }

}