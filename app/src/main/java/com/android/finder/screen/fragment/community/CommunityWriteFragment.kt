package com.android.finder.screen.fragment.community

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import com.android.finder.databinding.FragmentCommunityWriteBinding
import com.android.finder.screen.CommonFragment
import com.android.finder.R
import com.android.finder.component.RecyclerViewHorizonItemDeco
import com.android.finder.oneButtonDialogShow
import com.android.finder.screen.dialog.MBTISelectDialog
import com.android.finder.viewmodel.CommunityWriteViewModel
import gun0912.tedimagepicker.builder.TedImagePicker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File

class CommunityWriteFragment: CommonFragment<FragmentCommunityWriteBinding>(R.layout.fragment_community_write), View.OnClickListener, TextWatcher {

    private val writeViewModel : CommunityWriteViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.writeViewModel = writeViewModel
        context?.let {
            binding.imageRecyclerView.addItemDecoration(
                RecyclerViewHorizonItemDeco(it,8)
            )
        }

    }

    override fun eventListenerSetting() {
        binding.curiousButton.setOnClickListener(this)
        binding.backButton.setOnClickListener(this)
        binding.writeButton.setOnClickListener(this)
        binding.selectMBTILayout.setOnClickListener(this)
        binding.imageAddButton.setOnClickListener(this)

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
            binding.imageAddButton -> {
                context?.let {
                    TedImagePicker.with(it).max(3, R.string.msg_max_image_count).startMultiImage { uriList ->
                        uriList.forEach { uri ->
                            getFullPathFromUri(it, uri)?.let { path -> writeViewModel.questionImages.add(path) }
                        }
                    }
                }

            }
            binding.writeButton -> {
                isLoading = true
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
                    isLoading = false
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