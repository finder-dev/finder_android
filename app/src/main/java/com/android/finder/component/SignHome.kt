package com.android.finder.component

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.android.finder.ui.theme.FinderTheme
import com.android.finder.ui.theme.MainColor

@Composable
fun SignHome() {
    FinderTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MainColor
        ) {

        }
    }
}