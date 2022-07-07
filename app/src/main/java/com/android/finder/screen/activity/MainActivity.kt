package com.android.finder.screen.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.android.finder.R
import com.android.finder.databinding.ActivityMainBinding
import com.android.finder.oneButtonDialogShow
import com.android.finder.util.SecureManager

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



