package com.android.finder.screen.fragment.my

import android.os.Bundle
import android.view.View
import com.android.finder.R
import com.android.finder.databinding.FragmentSettingBinding
import com.android.finder.screen.CommonFragment
import com.suke.widget.SwitchButton




class SettingFragment: CommonFragment<FragmentSettingBinding>(R.layout.fragment_setting), View.OnClickListener {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.actionBar.titleView.text = resources.getString(R.string.setting)
    }
    override fun eventListenerSetting() {
        binding.actionBar.backButton.setOnClickListener(this)
        binding.moveToPrivacyModifyButton.setOnClickListener(this)
        binding.termsOfUseServiceButton.setOnClickListener(this)
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
        }
    }
}