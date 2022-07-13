package com.android.finder.screen.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.View
import androidx.databinding.DataBindingUtil
import com.android.finder.R
import com.android.finder.databinding.DialogTwoButtonBinding

class TwoButtonDialog(
    context: Context,
    val message: String,
    val subMessage: String? = null,
    val closeButtonTitle: String,
    val confirmButtonTitle: String
) : Dialog(context),
    View.OnClickListener {

    private val binding by lazy {
        DataBindingUtil.inflate<DialogTwoButtonBinding>(
            layoutInflater,
            R.layout.dialog_two_button,
            null,
            false
        )
    }
    var okEvent: () -> Unit = {}

    override fun onClick(v: View?) {
        when(v) {
            binding.confirmButton -> okEvent()
        }
        dismiss()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.mainDescriptionView.movementMethod = ScrollingMovementMethod()
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setCancelable(false)
        binding.mainDescriptionView.text = message
        binding.confirmButton.setOnClickListener(this)
        binding.cancelButton.setOnClickListener(this)
        if(subMessage.isNullOrBlank()) {
            binding.subDescriptionView.visibility = View.GONE
        } else {
            binding.subDescriptionView.text = subMessage
        }
        binding.cancelButton.text = closeButtonTitle
        binding.confirmButton.text = confirmButtonTitle
    }

    fun setEvent(event : () -> Unit) {
        okEvent = event
    }
}