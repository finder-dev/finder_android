package com.android.finder.screen.fragment.debate

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.android.finder.*
import com.android.finder.caching.CachingData
import com.android.finder.databinding.FragmentDebateDetailBinding
import com.android.finder.network.response.DebateDetailResponseVO
import com.android.finder.result.StringResult
import com.android.finder.screen.CommonFragment
import com.android.finder.screen.dialog.SelectListBottomSheetDialog
import com.android.finder.screen.fragment.community.CommunityDetailFragmentDirections
import com.android.finder.viewmodel.DebateDetailViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.lang.Exception

class DebateDetailFragment :
    CommonFragment<FragmentDebateDetailBinding>(R.layout.fragment_debate_detail),
    View.OnClickListener, TextWatcher {

    private val detailVieWModel: DebateDetailViewModel by viewModels()
    private val args: DebateDetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.detailViewModel = detailVieWModel
        refresh()
    }

    override fun onResume() {
        super.onResume()
        if (!EventBus.getDefault().isRegistered(this)) EventBus.getDefault().register(this)
    }

    override fun onPause() {
        super.onPause()
        if (EventBus.getDefault().isRegistered(this)) EventBus.getDefault().unregister(this)
    }

    private fun refresh() {
        isLoading = true
        CoroutineScope(Dispatchers.IO).launch {
            try {
                detailVieWModel.getDebateDetail(args.debateId)
            } catch (e: Exception) {
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
            detailVieWModel.detailResultMessage
        ) {
            navPopStack()
        }
    }

    private fun setUI(data: DebateDetailResponseVO) {
        balanceGameOnOff(data.join, (data.joinOption == "A"))
        binding.balanceGameView.apply {
            emptyIncludeView.root.visibility = View.GONE
            balanceGameTitleView.text = data.debateTitle
            remainingTimeView.text = data.deadline
            gamePossibleCount.text = data.optionACount.toString()
            balancePossibleButton.text = data.optionA
            gameImpossibleCount.text = data.optionBCount.toString()
            balanceImpossibleButton.text = data.optionB
        }
        binding.includePublisherData.apply {
            postUserMbtiView.text = data.writerMBTI
            postDateView.text =
                resources.getString(R.string.commentCountFormat, data.answerCount.toString())
            userNicknameView.text = data.writerNickname
        }
    }

    private fun balanceGameOnOff(join: Boolean, isA: Boolean) {
        if (join) {
            balanceGameOptionAOnOff(isA)
            balanceGameOptionBOnOff(!isA)
        } else {
            balanceGameOptionAOnOff(false)
            balanceGameOptionBOnOff(false)
        }
    }

    private fun balanceGameOptionAOnOff(
        isOn: Boolean
    ) {
        val onCountParams = ConstraintLayout.LayoutParams(
            ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT
        )
        onCountParams.topToTop = ConstraintLayout.LayoutParams.PARENT_ID
        onCountParams.rightToRight = ConstraintLayout.LayoutParams.PARENT_ID
        val offCountParams = ConstraintLayout.LayoutParams(
            ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT
        )
        offCountParams.topToTop = ConstraintLayout.LayoutParams.PARENT_ID
        offCountParams.rightToRight = ConstraintLayout.LayoutParams.PARENT_ID
        context?.let {
            onCountParams.rightMargin = 12.dpToPx(it)
            offCountParams.leftMargin = 12.dpToPx(it)
            offCountParams.topMargin = 20.dpToPx(it)
        }
        binding.balanceGameView.apply {
            if (isOn) {
                possibleCutImageView.visibility = View.VISIBLE
                gamePossibleCount.layoutParams = onCountParams
                gamePossibleCount.setTextColorResource(R.color.navy)
                balancePossibleButton.setBackgroundColor(resources.getColor(R.color.navy, null))
                balancePossibleButton.setTextColorResource(R.color.white)
            } else {
                possibleCutImageView.visibility = View.INVISIBLE
                gamePossibleCount.layoutParams = offCountParams
                gamePossibleCount.setTextColorResource(R.color.gray3)
                balancePossibleButton.setBackgroundColor(resources.getColor(R.color.white, null))
                balancePossibleButton.setTextColorResource(R.color.gray3)
            }
        }
    }

    private fun balanceGameOptionBOnOff(
        isOn: Boolean
    ) {
        val onCountParams = ConstraintLayout.LayoutParams(
            ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT
        )
        onCountParams.topToTop = ConstraintLayout.LayoutParams.PARENT_ID
        onCountParams.leftToLeft = ConstraintLayout.LayoutParams.PARENT_ID
        val offCountParams = ConstraintLayout.LayoutParams(
            ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT
        )
        offCountParams.topToTop = ConstraintLayout.LayoutParams.PARENT_ID
        offCountParams.leftToLeft = ConstraintLayout.LayoutParams.PARENT_ID
        context?.let {
            onCountParams.rightMargin = 12.dpToPx(it)
            offCountParams.leftMargin = 12.dpToPx(it)
            offCountParams.topMargin = 20.dpToPx(it)
        }
        binding.balanceGameView.apply {
            if (isOn) {
                impossibleCutImageView.visibility = View.VISIBLE
                gameImpossibleCount.layoutParams = onCountParams
                gameImpossibleCount.setTextColorResource(R.color.navy)
                balanceImpossibleButton.setBackgroundColor(resources.getColor(R.color.navy, null))
                balanceImpossibleButton.setTextColorResource(R.color.white)
            } else {
                impossibleCutImageView.visibility = View.INVISIBLE
                gameImpossibleCount.layoutParams = offCountParams
                gameImpossibleCount.setTextColorResource(R.color.gray3)
                balanceImpossibleButton.setBackgroundColor(resources.getColor(R.color.white, null))
                balanceImpossibleButton.setTextColorResource(R.color.gray3)
            }
        }
    }

    private fun modifyComment(answerId: Long, content : String) {
        isLoading = true
        CoroutineScope(Dispatchers.IO).launch {
            val message = detailVieWModel.modifyAnswers(answerId, content)
            toastShow(context, message.ifEmpty { resources.getString(R.string.msg_success_write) })
            isLoading = false
            refresh()
        }
    }

    private fun createReComment(answerId: Long, content : String) {
        isLoading = true
        CoroutineScope(Dispatchers.IO).launch {
            val message = detailVieWModel.createReAnswer(answerId, content)
            toastShow(context, message.ifEmpty { resources.getString(R.string.msg_success_write) })
            isLoading = false
            refresh()
        }
    }

    private fun deleteComment(answerId : Long, isComment : Boolean = true) {
        isLoading = true
        CoroutineScope(Dispatchers.IO).launch {
            val message = detailVieWModel.deleteAnswers(answerId)
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
            val message = detailVieWModel.reportAnswers(answerId)
            toastShow(context, message.ifEmpty { resources.getString(R.string.msg_report_complete) })
            isLoading = false
            refresh()
        }
    }

    override fun eventListenerSetting() {
        binding.commentInputButton.setOnClickListener(this)
        binding.reportButton.setOnClickListener(this)
        binding.balanceGameView.optionALayout.setOnClickListener(this)
        binding.balanceGameView.optionBLayout.setOnClickListener(this)
        binding.backButton.setOnClickListener(this)
        binding.commentEditTextView.addTextChangedListener(this)

        detailVieWModel.detailData.observe(viewLifecycleOwner) {
            if (it != null) {
                setUI(it)
            } else {
                errorDialog()
            }
        }
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.commentInputButton -> {
                CoroutineScope(Dispatchers.IO).launch {
                    val content = binding.commentEditTextView.text.toString()
                    val message = if (content.isNotEmpty()) {
                        val result = detailVieWModel.createAnswer(args.debateId, content)
                        result.ifEmpty {
                            binding.commentEditTextView.setText("")
                            App.instance.resources.getString(R.string.msg_comment_add)
                        }
                    } else {
                        resources.getString(R.string.error_empty_comment)
                    }
                    refresh()
                    toastShow(context, message)
                }
            }
            binding.balanceGameView.optionALayout, binding.balanceGameView.optionBLayout -> {
                val detailData = detailVieWModel.detailData.value
                isLoading = true
                val option = when(v) {
                    binding.balanceGameView.optionALayout -> "A"
                    else -> "B"
                }
                CoroutineScope(Dispatchers.IO).launch {
                    if(detailData != null) {
                        val message = detailVieWModel.joinDebate(detailData.debateId, option)
                        if(message.isNotEmpty()) toastShow(context, message)
                        else refresh()
                        isLoading = false
                    }
                }
            }
            binding.backButton -> navPopStack()
            binding.reportButton -> {
                twoButtonDialogShow(
                    context,
                    resources.getString(R.string.question_user_report),
                    resources.getString(R.string.msg_description_user_report),
                    closeButtonTitle = resources.getString(R.string.report),
                    confirmButtonTitle = resources.getString(R.string.cancel),
                    closeEvent = {
                        CoroutineScope(Dispatchers.IO).launch {
                            val message = detailVieWModel.reportContent(args.debateId)
                            oneButtonDialogShow(
                                context,
                                resources.getString(R.string.user_report_complete),
                                message
                            )
                        }
                    }
                )
            }
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
                                confirmButtonTitle = resources.getString(R.string.ok),
                                clickEvent = { deleteComment(commentData.answerId) }
                            )
                        }
                        resources.getString(R.string.send_a_note) -> {
                            navigate(DebateDetailFragmentDirections.actionDebateDetailFragmentToSendNoteFragment(commentData.userId))
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
                                closeButtonTitle = resources.getString(R.string.report),
                                confirmButtonTitle = resources.getString(R.string.cancel),
                                closeEvent = { reportComment(commentData.answerId) }
                            )
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
                                confirmButtonTitle = resources.getString(R.string.ok),
                                clickEvent = { deleteComment(reCommentData.id) }
                            )
                        }
                        resources.getString(R.string.send_a_note) -> {
                            navigate(DebateDetailFragmentDirections.actionDebateDetailFragmentToSendNoteFragment(reCommentData.userId))
                        }
                        resources.getString(R.string.report) -> {
                            twoButtonDialogShow(
                                context,
                                resources.getString(R.string.question_user_report),
                                resources.getString(R.string.msg_description_user_report),
                                closeButtonTitle = resources.getString(R.string.report),
                                confirmButtonTitle = resources.getString(R.string.cancel),
                                closeEvent = { reportComment(reCommentData.id) }
                            )
                        }
                    }
                    dismiss()
                }
            }
        }
        dialog.show(childFragmentManager, "reComment")
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        if(binding.commentEditTextView.text.toString().isNotEmpty()) {
            binding.commentInputButton.setBackgroundResource(R.drawable.bg_oval_main_color)
            binding.commentInputButton.setColorFilter(resources.getColor(
                R.color.white,
                null
            ))
        } else {
            binding.commentInputButton.setBackgroundResource(R.drawable.bg_oval_black7)
            binding.commentInputButton.setColorFilter(resources.getColor(
                R.color.black04,
                null
            ))
        }

    }

    override fun afterTextChanged(p0: Editable?) {}
}