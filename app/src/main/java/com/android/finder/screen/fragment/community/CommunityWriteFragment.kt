package com.android.finder.screen.fragment.community

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.android.finder.ImageDeleteEvent
import com.android.finder.databinding.FragmentCommunityWriteBinding
import com.android.finder.screen.CommonFragment
import com.android.finder.R
import com.android.finder.component.RecyclerViewHorizonItemDeco
import com.android.finder.enumdata.MBTI
import com.android.finder.oneButtonDialogShow
import com.android.finder.screen.dialog.GridSelectDialog
import com.android.finder.screen.dialog.MBTISelectDialog
import com.android.finder.toastShow
import com.android.finder.viewmodel.CommunityWriteViewModel
import gun0912.tedimagepicker.builder.TedImagePicker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.io.File

class CommunityWriteFragment: CommonFragment<FragmentCommunityWriteBinding>(R.layout.fragment_community_write), View.OnClickListener, TextWatcher {

    private val writeViewModel : CommunityWriteViewModel by viewModels()
    private val args : CommunityWriteFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.writeViewModel = writeViewModel
        context?.let {
            binding.imageRecyclerView.addItemDecoration(
                RecyclerViewHorizonItemDeco(it,8)
            )
        }
        modifyData()
    }

    override fun onResume() {
        super.onResume()
        if (!EventBus.getDefault().isRegistered(this)) EventBus.getDefault().register(this)
    }

    override fun onPause() {
        super.onPause()
        if (EventBus.getDefault().isRegistered(this)) EventBus.getDefault().unregister(this)
    }

    private fun modifyData() {
        if(args.communityId != 0L) {
            binding.contentsEditText.setText(args.communityContent)
            binding.titleEditText.setText(args.communityTitle)
            writeViewModel.selectedMbti.postValue(args.communityMbti)
            writeViewModel.isCurious = args.isCurious
            if(writeViewModel.isCurious) {
                binding.curiousButton.setImageResource(R.drawable.ic_curious_on)
            } else {
                binding.curiousButton.setImageResource(R.drawable.ic_curious_off)
            }
            if(args.images != null && args.images!!.isNotEmpty()) {
                writeViewModel.apply {
                    modifyImages = args.images!!
                    questionImages.clear()
                    questionImages.addAll(args.images!!.map { it.communityImageUrl })
                }
            }
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
            binding.selectedMbtiView.text = it.ifEmpty { resources.getString(R.string.select_target_mbti) }
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
                    GridSelectDialog(it, MBTI.getAllMbti(false)).apply {
                        selectEvent = { writeViewModel.selectedMbti.postValue(getItem() ?: "") }
                        show()
                    }
                }
            }
            binding.imageAddButton -> {
                context?.let {
                    TedImagePicker.with(it).max(3, R.string.msg_max_image_count).startMultiImage { uriList ->
                        uriList.forEach { uri ->
                            if(writeViewModel.questionImages.size < 3) {
                                getFullPathFromUri(it, uri)?.let { path ->
                                    writeViewModel.questionImages.add(path)
                                    writeViewModel.modifyImages.find { it.communityImageUrl == path }
                                        ?:writeViewModel.addImageUrls.add(path)
                                }
                            }
                        }
                    }
                }

            }
            binding.writeButton -> {
                isLoading = true
                val title = binding.titleEditText.text.toString()
                val content = binding.contentsEditText.text.toString()
                CoroutineScope(Dispatchers.IO).launch {
                    val isComplete = if(args.communityId == 0L) {
                        writeViewModel.writeContent(title, content)
                    } else {
                        writeViewModel.modifyContent(args.communityId, title, content)
                    }
                    if(isComplete) {
                        toastShow(context, writeViewModel.writeResultMessage)
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun imageDeleteEvent(event : ImageDeleteEvent) {
        writeViewModel.questionImages.remove(event.imageUrl)
        writeViewModel.modifyImages.find { it.communityImageUrl == event.imageUrl }?.let {
            writeViewModel.deleteImageIds.add(it.communityImageId)
        }
    }
}