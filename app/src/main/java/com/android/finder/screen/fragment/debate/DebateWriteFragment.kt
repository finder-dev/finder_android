package com.android.finder.screen.fragment.debate

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.android.finder.R
import com.android.finder.databinding.FragmentDebateWriteBinding
import com.android.finder.oneButtonDialogShow
import com.android.finder.screen.CommonFragment
import com.android.finder.viewmodel.DebateWriteViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DebateWriteFragment :
    CommonFragment<FragmentDebateWriteBinding>(R.layout.fragment_debate_write),
    View.OnClickListener, TextWatcher {

    private val writeViewModel: DebateWriteViewModel by viewModels()
    private val args: DebateWriteFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        modifyData()
    }

    private fun modifyData() {
        if (args.debateId != 0L) {
            binding.optionAEditText.setText(args.debateOptionA)
            binding.optionBEditText.setText(args.debateOptionB)
            binding.titleEditText.setText(args.debateTitle)
        }
    }

    override fun eventListenerSetting() {
        binding.writeButton.setOnClickListener(this)
        binding.backButton.setOnClickListener(this)
        binding.titleEditText.addTextChangedListener(this)
        binding.optionBEditText.addTextChangedListener(this)
        binding.optionAEditText.addTextChangedListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.backButton -> navPopStack()
            binding.writeButton -> {
                if(!isLoading) {
                    isLoading = true
                    val title = binding.titleEditText.text.toString()
                    val optionA = binding.optionAEditText.text.toString()
                    val optionB = binding.optionBEditText.text.toString()
                    CoroutineScope(Dispatchers.IO).launch {
                        val resultMessage = writeViewModel.createDebate(title, optionA, optionB)
                        oneButtonDialogShow(
                            context,
                            resources.getString(R.string.success_create_debate),
                            resultMessage
                        ) {
                            navPopStack()
                        }
                        isLoading = false
                    }
                }
            }
        }
    }

    private fun isDataFull() : Boolean {
        val title = binding.titleEditText.text.toString()
        val optionA = binding.optionAEditText.text.toString()
        val optionB = binding.optionBEditText.text.toString()
        return title.isNotEmpty() && optionA.isNotEmpty() && optionB.isNotEmpty()
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        binding.writeButton.isEnabled = isDataFull()
    }

    override fun afterTextChanged(p0: Editable?) {}
}