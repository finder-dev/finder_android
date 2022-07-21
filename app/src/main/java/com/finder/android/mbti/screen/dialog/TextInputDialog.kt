package com.finder.android.mbti.screen.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.View
import androidx.databinding.DataBindingUtil
import com.finder.android.mbti.R
import com.finder.android.mbti.databinding.DialogTextInputBinding
import com.finder.android.mbti.result.StringResult

class TextInputDialog(context: Context, private val message: String) : Dialog(context),
    View.OnClickListener {

    private val binding by lazy {
        DataBindingUtil.inflate<DialogTextInputBinding>(
            layoutInflater,
            R.layout.dialog_text_input,
            null,
            false
        )
    }

    var result : StringResult? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.mainDescriptionView.movementMethod = ScrollingMovementMethod()
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setCancelable(false)
        binding.mainDescriptionView.text = message
        binding.confirmButton.setOnClickListener(this)
        binding.cancelButton.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v) {
            binding.confirmButton -> {
                if(binding.inputDataView.text.toString().isNotEmpty()) {
                    result?.finish(binding.inputDataView.text.toString())
                } else return
            }
        }
        dismiss()
    }

}