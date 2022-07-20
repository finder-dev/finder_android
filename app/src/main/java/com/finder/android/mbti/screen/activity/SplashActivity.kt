package com.finder.android.mbti.screen.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.finder.android.mbti.util.SecureManager
import com.finder.android.mbti.util.SettingUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    companion object {
        private const val SPLASH_DELAY_TIME = 1500L
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        start()
    }

    //여기다 로딩 관련 내용 추가할 예정
    private fun start() {
        CoroutineScope(Dispatchers.Default).launch {
            val sendIntent = if (SecureManager(this@SplashActivity).getToken().isNotEmpty() &&
                SettingUtil.getAutoLoginData(this@SplashActivity)
            ) {
                Intent(baseContext, MainActivity::class.java)
            } else {
                Intent(baseContext, SignActivity::class.java)
            }
            delay(SPLASH_DELAY_TIME)
            sendIntent.run {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(this)
            }
            finish()
        }
    }
}