package com.android.finder.screen.dialog

import android.app.Dialog
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

class MBTISelectDialog(context: Context) : Dialog(context), View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    private val binding by lazy {
        DataBindingUtil.inflate<DialogSelectMbtiBinding>(
            layoutInflater,
            R.layout.dialog_select_mbti,
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
        binding.selectOneGroup.setOnCheckedChangeListener(this)
        binding.selectTwoGroup.setOnCheckedChangeListener(this)
        binding.selectThreeGroup.setOnCheckedChangeListener(this)
        binding.selectFourGroup.setOnCheckedChangeListener(this)
    }


    override fun onClick(p0: View?) {
        when(p0) {
            binding.confirmButton -> {
                selectEvent()
                dismiss()
            }
        }
    }

    fun getMBTI() : String? {
        return if(M.isNotEmpty() && B.isNotEmpty() && T.isNotEmpty() && I.isNotEmpty()) {
            "$M$B$T$I"
        } else null
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