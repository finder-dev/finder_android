package com.finder.android.mbti.screen.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.finder.android.mbti.databinding.ActivityMainBinding
import com.finder.android.mbti.R
import com.finder.android.mbti.oneButtonDialogShow
import com.finder.android.mbti.util.SecureManager

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        if(SecureManager(this).getToken().isEmpty()) {
            oneButtonDialogShow(this,resources.getString(R.string.error_login), resources.getString(R.string.msg_re_login))
            finish()
        }
    }
}



