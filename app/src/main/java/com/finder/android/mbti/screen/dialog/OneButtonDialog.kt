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
import com.finder.android.mbti.databinding.DialogOneButtonBinding

class OneButtonDialog(context: Context, val message: String, val subMessage : String? = null) : Dialog(context),
    View.OnClickListener {

    private val binding by lazy {
        DataBindingUtil.inflate<DialogOneButtonBinding>(
            layoutInflater,
            R.layout.dialog_one_button,
            null,
            false
        )
    }

    var okEvent : () -> Unit = {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.mainDescriptionView.movementMethod = ScrollingMovementMethod()
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setCancelable(false)
        binding.mainDescriptionView.text = message
        binding.confirmButton.setOnClickListener(this)
        if(subMessage.isNullOrBlank()) {
            binding.subDescriptionView.visibility = View.GONE
        } else {
            binding.subDescriptionView.text = subMessage
        }
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.confirmButton -> {
                okEvent()
                this.dismiss()
            }
        }
    }

    fun setEvent(event : () -> Unit) {
        okEvent = event
    }
}