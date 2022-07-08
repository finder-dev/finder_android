package com.android.finder.screen.fragment.community

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.fragment.app.viewModels
import com.android.finder.databinding.FragmentCommunityWriteBinding
import com.android.finder.screen.CommonFragment
import com.android.finder.R
import com.android.finder.oneButtonDialogShow
import com.android.finder.screen.dialog.MBTISelectDialog
import com.android.finder.viewmodel.CommunityWriteViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CommunityWriteFragment: CommonFragment<FragmentCommunityWriteBinding>(R.layout.fragment_community_write), View.OnClickListener, TextWatcher {

    private val writeViewModel : CommunityWriteViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun eventListenerSetting() {
        binding.curiousButton.setOnClickListener(this)
        binding.backButton.setOnClickListener(this)
        binding.writeButton.setOnClickListener(this)
        binding.selectMBTILayout.setOnClickListener(this)

        binding.titleEditText.addTextChangedListener(this)
        binding.contentsEditText.addTextChangedListener(this)

        writeViewModel.selectedMbti.observe(viewLifecycleOwner) {
            binding.selectedMbtiView.text = if(it.isNotEmpty()) it else resources.getString(R.string.select_target_mbti)
        }
    }

    override fun onClick(v: View?) {
        when(v) {
            binding.curiousButton -> {
                writeViewModel.isCurious = !writeViewModel.isCurious
                if(writeViewModel.isCurious) {
                    binding.curiousButton.setImageResource(R.drawable.ic_curious_on)
                } else {
                    binding.curiousButton.setImageResource(R.drawable.ic_curious_off)
                }
            }
            binding.selectMBTILayout -> {
                context?.let {
                    MBTISelectDialog(it).apply {
                        selectEvent = { writeViewModel.selectedMbti.postValue(getMBTI() ?: "") }
                        show()
                    }
                }
            }
            binding.writeButton -> {
                val title = binding.titleEditText.text.toString()
                val content = binding.contentsEditText.text.toString()
                CoroutineScope(Dispatchers.IO).launch {
                    val isWrite = writeViewModel.writeContent(title, content)

                    if(isWrite) {
                        oneButtonDialogShow(context, resources.getString(R.string.success_write), writeViewModel.writeResultMessage)
                        navPopStack()
                    } else {
                        oneButtonDialogShow(context, resources.getString(R.string.error_write), writeViewModel.writeResultMessage)
                    }
                }
            }
            binding.backButton -> navPopStack()
        }
    }

    private fun isPostConditionEnable() : Boolean {
        return binding.titleEditText.text.toString().isNotEmpty() && binding.contentsEditText.text.toString().length >= 10
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        binding.writeButton.isEnabled = isPostConditionEnable()
    }

    override fun afterTextChanged(p0: Editable?) {}
}