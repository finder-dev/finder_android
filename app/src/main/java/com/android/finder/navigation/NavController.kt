package com.android.finder.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.android.finder.component.SignHome

@Composable
fun setUpNavController(controller : NavHostController) {

    NavHost(navController = controller, startDestination = "signHome") {

        composable("signHome") {
            SignHome()
        }
    }
}