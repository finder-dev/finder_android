package com.android.finder.screen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavHost
import androidx.navigation.compose.rememberNavController
import com.android.finder.R
import com.android.finder.navigation.setUpNavController
import com.android.finder.ui.theme.FinderTheme
import com.android.finder.ui.theme.MainColor

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContent {
            FinderTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MainColor
                ) {
                    setUpNavController(controller = rememberNavController())
                }
            }
        }
    }
}



