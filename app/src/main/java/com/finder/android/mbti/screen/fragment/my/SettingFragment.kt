package com.finder.android.mbti.screen.fragment.my

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.finder.android.mbti.BuildConfig
import com.finder.android.mbti.R
import com.finder.android.mbti.databinding.FragmentSettingBinding
import com.finder.android.mbti.oneButtonDialogShow
import com.finder.android.mbti.CommonFragment
import com.finder.android.mbti.screen.activity.SignActivity
import com.finder.android.mbti.twoButtonDialogShow
import com.finder.android.mbti.util.SecureManager
import com.finder.android.mbti.util.SettingUtil
import com.finder.android.mbti.viewmodel.SettingViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class SettingFragment: CommonFragment<FragmentSettingBinding>(R.layout.fragment_setting), View.OnClickListener {

    private val settingViewModel : SettingViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.actionBar.titleView.text = resources.getString(R.string.setting)
        binding.appVersionView.text =
            resources.getString(R.string.app_version_format, BuildConfig.VERSION_NAME)
    }
    override fun eventListenerSetting() {
        binding.actionBar.backButton.setOnClickListener(this)
        binding.moveToPrivacyModifyButton.setOnClickListener(this)
        binding.termsOfUseServiceButton.setOnClickListener(this)
        binding.accountDeletionButton.setOnClickListener(this)
        binding.alarmReceptionSwitchButton.setOnCheckedChangeListener { view, isChecked ->

        }
    }

    override fun onClick(v: View?) {
        when(v) {
            binding.actionBar.backButton -> navPopStack()
            binding.moveToPrivacyModifyButton -> {
                navigate(SettingFragmentDirections.actionSettingFragmentToUserInformationFragment())
            }
            binding.termsOfUseServiceButton -> {
                navigate(SettingFragmentDirections.actionSettingFragmentToWebFragment(
                    "https://pineapple-session-93c.notion.site/513cc9a19e4f40c491b43fa025340898"
                ))
            }
            binding.accountDeletionButton -> {
                twoButtonDialogShow(
                    context,
                    resources.getString(R.string.question_delete_user),
                    resources.getString(R.string.msg_delete_user_sub),
                    resources.getString(R.string.deletion),
                    resources.getString(R.string.no),
                    closeEvent = {
                        isLoading = true
                        CoroutineScope(Dispatchers.IO).launch {
                            if(settingViewModel.deletionUser()) {
                                oneButtonDialogShow(
                                    context,
                                    resources.getString(R.string.success_delete_user),
                                    resources.getString(R.string.msg_success_delete_user)
                                ) {
                                    CoroutineScope(Dispatchers.Main).launch {
                                        context?.let {
                                            SettingUtil.setAutoLoginKey(it, false)
                                            SecureManager(it).removeToken()
                                        }
                                        val sendIntent = Intent(context, SignActivity::class.java)
                                        sendIntent.run {
                                            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                                            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                            startActivity(this)
                                        }
                                        activity?.finish()
                                    }
                                }
                            } else {
                                oneButtonDialogShow(
                                    context,
                                    resources.getString(R.string.error_delete_user),
                                    resources.getString(R.string.error_unspecified_message)
                                )
                            }
                            isLoading = false
                        }
                    }
                )
            }
        }
    }
}