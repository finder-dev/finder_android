package com.android.finder.screen.fragment.community

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.android.finder.*
import com.android.finder.caching.CachingData
import com.android.finder.component.RecyclerViewHorizonItemDeco
import com.android.finder.databinding.FragmentCommunityDetailBinding
import com.android.finder.dataobj.CommunityDetailDto
import com.android.finder.result.StringResult
import com.android.finder.screen.CommonFragment
import com.android.finder.screen.dialog.SelectListBottomSheetDialog
import com.android.finder.screen.fragment.MainFragmentDirections
import com.android.finder.viewmodel.CommunityDetailViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
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

    override fun onResume() {
        super.onResume()
        if (!EventBus.getDefault().isRegistered(this)) EventBus.getDefault().register(this)
    }

    override fun onPause() {
        super.onPause()
        if (EventBus.getDefault().isRegistered(this)) EventBus.getDefault().unregister(this)
    }

    override fun eventListenerSetting() {
        binding.backButton.setOnClickListener(this)
        binding.commentInputButton.setOnClickListener(this)
        binding.likeLayout.setOnClickListener(this)
        binding.saveButton.setOnClickListener(this)
        binding.attributeButton.setOnClickListener(this)

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
            binding.saveButton.setImageResource(if (saveUser) R.drawable.ic_save_on else R.drawable.ic_save_off)
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
                    toastShow(context, toastMessage)
                }
            }
            binding.attributeButton -> {
                val detailData = communityDetailViewModel.communityDetailData.value
                if (detailData != null) {
                    val itemList = if (detailData.userId == CachingData.userProfile?.userId) {
                        arrayListOf(
                            resources.getString(R.string.modify),
                            resources.getString(R.string.delete),
                            resources.getString(R.string.close)
                        )
                    } else {
                        arrayListOf(
                            resources.getString(R.string.send_a_note),
                            resources.getString(R.string.report),
                            resources.getString(R.string.close)
                        )
                    }
                    val dialog = SelectListBottomSheetDialog(itemList).apply {
                        result = object : StringResult {
                            override fun finish(data: String) {
                                when (data) {
                                    resources.getString(R.string.modify) -> {
                                        //수정 화면으로 이동
                                        navigate(
                                            CommunityDetailFragmentDirections.actionCommunityDetailFragmentToCommunityWriteFragment(
                                                communityId = detailData.communityId,
                                                communityTitle = detailData.communityTitle,
                                                communityContent = detailData.communityContent,
                                                communityMbti = detailData.communityMBTI,
                                                isCurious = detailData.isQuestion,
                                                images = detailData.communityImgDtos.toTypedArray()
                                            )
                                        )
                                    }
                                    resources.getString(R.string.delete) -> {
                                        this@CommunityDetailFragment.context?.let {
                                            twoButtonDialogShow(
                                                it,
                                                resources.getString(R.string.question_delete_content),
                                                closeButtonTitle = resources.getString(R.string.no),
                                                confirmButtonTitle = resources.getString(R.string.ok)
                                            ) {
                                                CoroutineScope(Dispatchers.IO).launch {
                                                    if (communityDetailViewModel.deleteCommunityContent(
                                                            detailData.communityId
                                                        )
                                                    ) { navPopStack() }
                                                    toastShow(
                                                        it,
                                                        communityDetailViewModel.deleteContentResultMessage
                                                    )
                                                }
                                            }
                                        }
                                    }
                                    resources.getString(R.string.send_a_note) -> {
                                        //쪽지 보내기 기능
                                        navigate(CommunityDetailFragmentDirections.actionCommunityDetailFragmentToSendNoteFragment(detailData.userId))
                                    }
                                    resources.getString(R.string.report) -> {
                                        //신고 기능
                                        this@CommunityDetailFragment.context?.let {
                                            twoButtonDialogShow(
                                                it,
                                                resources.getString(R.string.question_user_report),
                                                resources.getString(R.string.msg_description_user_report),
                                                closeButtonTitle = resources.getString(R.string.cancel),
                                                confirmButtonTitle = resources.getString(R.string.report)
                                            ) {
                                                CoroutineScope(Dispatchers.IO).launch {
                                                    if (communityDetailViewModel.reportContent(
                                                            detailData.communityId
                                                        )
                                                    ) {
                                                        oneButtonDialogShow(
                                                            it,
                                                            App.instance.resources.getString(R.string.user_report_complete),
                                                            App.instance.resources.getString(R.string.msg_report_complete)
                                                        )
                                                    } else {
                                                        toastShow(
                                                            it,
                                                            communityDetailViewModel.reportContentResultMessage
                                                        )
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                dismiss()
                            }
                        }
                    }
                    dialog.show(childFragmentManager, "attribute")
                }
            }
            binding.commentInputButton -> {
                if (communityDetailViewModel.answerId == 0) {
                    CoroutineScope(Dispatchers.IO).launch {
                        val item = communityDetailViewModel.communityDetailData.value
                        val toastMessage: String
                        val answer = binding.commentEditTextView.text.toString()
                        toastMessage = if (answer.isNotEmpty()) {
                            if (item != null && communityDetailViewModel.createAnswer(
                                    item.communityId,
                                    answer
                                )
                            ) resources.getString(R.string.msg_comment_add)
                            else resources.getString(R.string.error_comment_add)
                        } else resources.getString(R.string.error_empty_comment)
                        refresh()
                        toastShow(context, toastMessage)
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
                    toastShow(context, toastMessage)
                }
            }
        }
    }

    private fun modifyComment(answerId: Long, content : String) {
        isLoading = true
        CoroutineScope(Dispatchers.IO).launch {
            val message = communityDetailViewModel.modifyAnswers(answerId, content)
            toastShow(context, message.ifEmpty { resources.getString(R.string.msg_success_write) })
            isLoading = false
            refresh()
        }
    }

    private fun createReComment(answerId: Long, content : String) {
        isLoading = true
        CoroutineScope(Dispatchers.IO).launch {
            val message = communityDetailViewModel.createReAnswer(answerId, content)
            toastShow(context, message.ifEmpty { resources.getString(R.string.msg_success_write) })
            isLoading = false
            refresh()
        }
    }

    private fun deleteComment(answerId : Long, isComment : Boolean = true) {
        isLoading = true
        CoroutineScope(Dispatchers.IO).launch {
            val message = communityDetailViewModel.deleteAnswers(answerId)
            toastShow(context, message.ifEmpty {
                if (isComment) {
                    resources.getString(R.string.msg_delete_content)
                } else {
                    resources.getString(R.string.msg_delete_re_comment)
                }
            })
            isLoading = false
            refresh()
        }
    }

    private fun reportComment(answerId: Long) {
        isLoading = true
        CoroutineScope(Dispatchers.IO).launch {
            val message = communityDetailViewModel.reportAnswers(answerId)
            toastShow(context, message.ifEmpty { resources.getString(R.string.msg_report_complete) })
            isLoading = false
            refresh()
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun commentAttributeClick(event : CommentAttributeClickEvent) {
        val commentData = event.data
        val itemList = if (commentData.userId == CachingData.userProfile?.userId) {
            arrayListOf(
                resources.getString(R.string.modify),
                resources.getString(R.string.delete),
                resources.getString(R.string.close)
            )
        } else {
            arrayListOf(
                resources.getString(R.string.send_a_note),
                resources.getString(R.string.re_comment_put_on),
                resources.getString(R.string.report),
                resources.getString(R.string.close)
            )
        }
        val dialog = SelectListBottomSheetDialog(itemList).apply {
            result = object : StringResult {
                override fun finish(data: String) {
                    when(data) {
                        resources.getString(R.string.modify) -> {
                            textInputDialogShow(
                                context,
                                resources.getString(R.string.comment_modify),
                                object : StringResult {
                                    override fun finish(data: String) {
                                        modifyComment(commentData.answerId, data)
                                    }
                                }
                            )
                        }
                        resources.getString(R.string.delete) -> {
                            twoButtonDialogShow(
                                context,
                                resources.getString(R.string.question_delete_content),
                                closeButtonTitle = resources.getString(R.string.no),
                                confirmButtonTitle = resources.getString(R.string.ok)
                            ) { deleteComment(commentData.answerId) }
                        }
                        resources.getString(R.string.send_a_note) -> {
                            navigate(CommunityDetailFragmentDirections.actionCommunityDetailFragmentToSendNoteFragment(commentData.userId))
                        }
                        resources.getString(R.string.re_comment_put_on) -> {
                            textInputDialogShow(
                                context,
                                resources.getString(R.string.re_comment_put_on),
                                object : StringResult {
                                    override fun finish(data: String) {
                                        createReComment(commentData.answerId, data)
                                    }
                                }
                            )
                        }
                        resources.getString(R.string.report) -> {
                            twoButtonDialogShow(
                                context,
                                resources.getString(R.string.question_user_report),
                                resources.getString(R.string.msg_description_user_report),
                                closeButtonTitle = resources.getString(R.string.no),
                                confirmButtonTitle = resources.getString(R.string.ok)
                            ) { reportComment(commentData.answerId) }
                        }
                    }
                    dismiss()
                }
            }
        }
        dialog.show(childFragmentManager, "comment")
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun reCommentAttributeClick(event : ReCommentAttributeClickEvent) {
        val reCommentData = event.data
        val itemList = if (reCommentData.userId == CachingData.userProfile?.userId) {
            arrayListOf(
                resources.getString(R.string.modify),
                resources.getString(R.string.delete),
                resources.getString(R.string.close)
            )
        } else {
            arrayListOf(
                resources.getString(R.string.send_a_note),
                resources.getString(R.string.report),
                resources.getString(R.string.close)
            )
        }
        val dialog = SelectListBottomSheetDialog(itemList).apply {
            result = object : StringResult {
                override fun finish(data: String) {
                    when(data) {
                        resources.getString(R.string.modify) -> {
                            textInputDialogShow(
                                context,
                                resources.getString(R.string.re_comment_modify),
                                object : StringResult {
                                    override fun finish(data: String) {
                                        modifyComment(reCommentData.id, data)
                                    }
                                }
                            )
                        }
                        resources.getString(R.string.delete) -> {
                            twoButtonDialogShow(
                                context,
                                resources.getString(R.string.question_delete_content),
                                closeButtonTitle = resources.getString(R.string.no),
                                confirmButtonTitle = resources.getString(R.string.ok)
                            ) { deleteComment(reCommentData.id) }
                        }
                        resources.getString(R.string.send_a_note) -> {
                            navigate(CommunityDetailFragmentDirections.actionCommunityDetailFragmentToSendNoteFragment(reCommentData.userId))
                        }
                        resources.getString(R.string.report) -> {
                            twoButtonDialogShow(
                                context,
                                resources.getString(R.string.question_user_report),
                                resources.getString(R.string.msg_description_user_report),
                                closeButtonTitle = resources.getString(R.string.no),
                                confirmButtonTitle = resources.getString(R.string.ok)
                            ) { reportComment(reCommentData.id) }
                        }
                    }
                    dismiss()
                }
            }
        }
        dialog.show(childFragmentManager, "reComment")
    }
}