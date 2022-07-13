package com.android.finder.screen.fragment.community

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.android.finder.App
import com.android.finder.R
import com.android.finder.ToastShow
import com.android.finder.component.RecyclerViewHorizonItemDeco
import com.android.finder.component.RecyclerViewItemDeco
import com.android.finder.databinding.FragmentCommunityDetailBinding
import com.android.finder.dataobj.CommunityDetailDto
import com.android.finder.oneButtonDialogShow
import com.android.finder.screen.CommonFragment
import com.android.finder.viewmodel.CommunityDetailViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class CommunityDetailFragment :
    CommonFragment<FragmentCommunityDetailBinding>(R.layout.fragment_community_detail),
    View.OnClickListener {

    private val communityDetailViewModel: CommunityDetailViewModel by viewModels()
    private val args: CommunityDetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.detailViewModel = communityDetailViewModel
        refresh()
        context?.let {
            binding.imageRecyclerView.addItemDecoration(
                RecyclerViewHorizonItemDeco(it, 8)
            )
        }
    }

    override fun eventListenerSetting() {
        binding.backButton.setOnClickListener(this)
        binding.commentInputButton.setOnClickListener(this)
        binding.likeLayout.setOnClickListener(this)
        binding.saveButton.setOnClickListener(this)

        communityDetailViewModel.communityDetailData.observe(viewLifecycleOwner) {
            if (it != null) setUI(it)
            else {
                errorDialog()
            }
        }
    }

    private fun refresh() {
        isLoading = true
        CoroutineScope(Dispatchers.IO).launch {
            try {
                communityDetailViewModel.getCommunityContentDetail(args.communityId)
            } catch (e: Exception) {
                //다이얼로그 + 이전 화면으로 이동
                errorDialog()
                e.printStackTrace()
            }
            isLoading = false
        }
    }

    private fun errorDialog() {
        oneButtonDialogShow(
            context,
            resources.getString(R.string.error_community_detail_load),
            communityDetailViewModel.detailResultMessage
        ) {
            navPopStack()
        }
    }

    private fun setUI(data: CommunityDetailDto) {
        data.apply {
            binding.questionMbtiView.text = communityMBTI
            binding.isCuriousImageView.isVisible = isQuestion
            binding.communityTitleTextView.text = communityTitle
            binding.communityContentsTextView.text = communityContent
            binding.includePublisherData.userNicknameView.text = userNickname
            binding.includePublisherData.postDateView.text = createTime
            binding.includePublisherData.postUserMbtiView.text = userMBTI
            binding.likeButton.setColorFilter(
                if (likeUser) App.instance.resources.getColor(
                    R.color.mainColor,
                    null
                ) else App.instance.resources.getColor(R.color.black7, null)
            )
            binding.likeCountView.text =
                resources.getString(R.string.likeCountFormat, likeCount.toString())
            binding.commentCountView.text =
                resources.getString(R.string.commentCountFormat, answerCount.toString())
            binding.saveButton.setImageResource(if(saveUser) R.drawable.ic_save_on else R.drawable.ic_save_off)
        }
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.backButton -> navPopStack()
            binding.saveButton -> {
                CoroutineScope(Dispatchers.IO).launch {
                    val item = communityDetailViewModel.communityDetailData.value
                    var toastMessage = ""
                    if (item != null && communityDetailViewModel.saveChange(args.communityId)) {
                        item.saveUser = !item.saveUser
                        toastMessage =
                            resources.getString(if (item.saveUser) R.string.msg_save_success else R.string.msg_save_delete)
                        CoroutineScope(Dispatchers.Main).launch {
                            try {
                                setUI(item)
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }
                    } else {
                        toastMessage = resources.getString(R.string.error_unspecified_message)
                    }
                    ToastShow(context, toastMessage)
                }
            }
            binding.commentInputButton -> {
                if(communityDetailViewModel.answerId == 0) {
                    CoroutineScope(Dispatchers.IO).launch {
                        val item = communityDetailViewModel.communityDetailData.value
                        var toastMessage = ""
                        val answer = binding.commentEditTextView.text.toString()
                        if(answer.isNotEmpty()) {
                            if(item != null && communityDetailViewModel.createAnswer(item.communityId, answer)) {
                                toastMessage = resources.getString(R.string.msg_comment_add)
                            } else {
                                toastMessage = resources.getString(R.string.error_comment_add)
                            }
                        } else toastMessage = resources.getString(R.string.error_empty_comment)
                        refresh()
                        ToastShow(context, toastMessage)
                    }
                }
                binding.commentEditTextView.setText("")
            }
            binding.likeLayout -> {
                isLoading = true
                CoroutineScope(Dispatchers.IO).launch {
                    val item = communityDetailViewModel.communityDetailData.value
                    var toastMessage = ""
                    if (item != null && communityDetailViewModel.likeChange(args.communityId)) {
                        item.likeUser = !item.likeUser
                        if (item.likeUser) {
                            toastMessage = resources.getString(R.string.msg_like_success)
                            item.likeCount++
                        } else {
                            toastMessage = resources.getString(R.string.msg_like_delete)
                            item.likeCount--
                        }
                        CoroutineScope(Dispatchers.Main).launch {
                            try {
                                setUI(item)
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }
                    } else {
                        toastMessage = resources.getString(R.string.error_unspecified_message)
                    }
                    isLoading = false
                    ToastShow(context, toastMessage)
                }
            }
        }
    }
}