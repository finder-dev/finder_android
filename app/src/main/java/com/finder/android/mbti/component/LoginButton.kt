package com.finder.android.mbti.component

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.databinding.DataBindingUtil
import com.finder.android.mbti.R
import com.finder.android.mbti.databinding.ItemLoginLayoutBinding

class LoginButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    style: Int = 0
) : LinearLayoutCompat(context, attrs, style) {

    var _binding: ItemLoginLayoutBinding? = null
    val binding: ItemLoginLayoutBinding get() = _binding!!


    init {
        _binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.item_login_layout,
            this,
            false
        )
        addView(binding.root)
        if(style == 0) getAttrs(attrs)
        else getAttrs(attrs, style)
    }

    private fun getAttrs(attrs: AttributeSet?) {
        attrs?.let {
            setTypeArray(typeArray = context.obtainStyledAttributes(it, R.styleable.LoginButton))
        }
    }

    private fun getAttrs(attrs: AttributeSet?, defStyle : Int){
        attrs?.let {
            setTypeArray(typeArray = context.obtainStyledAttributes(it, R.styleable.LoginButton, defStyle, 0))
        }
    }

    private fun setTypeArray(typeArray : TypedArray) {
        val backgroundColorId = typeArray.getColor(R.styleable.LoginButton_bgColor, 0)
        binding.root.setBackgroundColor(backgroundColorId)
        val iconId = typeArray.getResourceId(R.styleable.LoginButton_symbol, 0)
        binding.socialLoginIcon.setImageResource(iconId)
        val textColor = typeArray.getColor(R.styleable.LoginButton_textColor, 0)
        binding.socialLoginTextView.setTextColor(textColor)
        val text = typeArray.getString(R.styleable.LoginButton_text)
        binding.socialLoginTextView.text = text
    }
}
