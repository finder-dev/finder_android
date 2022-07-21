package com.finder.android.mbti.screen.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import com.finder.android.mbti.R
import com.finder.android.mbti.databinding.ActivitySignBinding
import com.finder.android.mbti.util.SettingUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivitySignBinding>(this@SignActivity, R.layout.activity_sign)
        CoroutineScope(Dispatchers.IO).launch {
            setNavigationGraph()
        }

    }

    private suspend fun setNavigationGraph() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController

        val navGraph = navController.navInflater.inflate(R.navigation.nav_sign)
        if(SettingUtil.getOnBoardingData(this@SignActivity)) {
            navGraph.setStartDestination(R.id.emailLoginFragment)
        } else {
            navGraph.setStartDestination(R.id.onBoardingFragment)
        }

        CoroutineScope(Dispatchers.Main).launch {
            navController.graph = navGraph
        }
    }
}