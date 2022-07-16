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
        binding.alarmReceptionSwitchButton.setOnCheckedChangeListener { view, isChecked ->

        }
    }

    override fun onClick(v: View?) {
        when(v) {
            binding.actionBar.backButton -> navPopStack()
            binding.moveToPrivacyModifyButton -> {
                //개인정보 수정 화면 추가 후 navigate 시키기
            }
        }
    }
}