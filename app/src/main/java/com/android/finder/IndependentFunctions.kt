package com.android.finder

import android.content.Context
import com.android.finder.screen.dialog.OneButtonDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

fun oneButtonDialogShow(context: Context?, message: String, subMessage : String? = null) {
    CoroutineScope(Dispatchers.Main).launch {
        try {
            context?.let {
                OneButtonDialog(it, message, subMessage).show()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

fun isValidEmail(email: String?): Boolean {
    if (email == null) return false
    val regex = "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,64}".toRegex()
    return regex.matches(email)
}