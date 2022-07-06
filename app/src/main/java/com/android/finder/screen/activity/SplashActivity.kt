package com.android.finder.screen.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.android.finder.R
import androidx.navigation.findNavController
import com.android.finder.util.SettingUtil
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
    private fun start(){
        val sendIntent = Intent(baseContext, SignActivity::class.java)
        CoroutineScope(Dispatchers.Default).launch {
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