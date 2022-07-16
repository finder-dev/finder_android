package com.android.finder

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.loader.content.CursorLoader
import androidx.recyclerview.widget.RecyclerView
import com.android.finder.result.StringResult
import com.android.finder.screen.dialog.GridSelectDialog
import com.android.finder.screen.dialog.OneButtonDialog
import com.android.finder.screen.dialog.TextInputDialog
import com.android.finder.screen.dialog.TwoButtonDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

fun toastShow(context: Context?, message: String) {
    CoroutineScope(Dispatchers.Main).launch {
        try {
            Toast.makeText(
                context,
                message,
                Toast.LENGTH_SHORT
            ).show()
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }
}

fun oneButtonDialogShow(
    context: Context?,
    message: String,
    subMessage: String? = null,
    clickEvent: () -> Unit = {}
) {
    CoroutineScope(Dispatchers.Main).launch {
        try {
            context?.let {
                OneButtonDialog(it, message, subMessage).apply { okEvent = clickEvent }.show()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

fun twoButtonDialogShow(
    context: Context?,
    message: String,
    subMessage: String? = null,
    closeButtonTitle: String,
    confirmButtonTitle: String,
    clickEvent: () -> Unit = {},
    closeEvent: () -> Unit = {}
) {
    CoroutineScope(Dispatchers.Main).launch {
        try {
            context?.let {
                TwoButtonDialog(
                    it,
                    message,
                    subMessage,
                    closeButtonTitle,
                    confirmButtonTitle
                ).apply {
                    okEvent = clickEvent
                    this.closeEvent = closeEvent
                }.show()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

fun textInputDialogShow(
    context: Context?,
    message: String,
    result: StringResult
) {
    CoroutineScope(Dispatchers.Main).launch {
        try {
            context?.let {
                TextInputDialog(
                    it,
                    message
                ).apply { this.result = result }.show()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

fun selectGridDialogShow(context: Context?, selectItems: List<String>, selectEvent: () -> Unit) {
    CoroutineScope(Dispatchers.Main).launch {
        try {
            context?.let {
                GridSelectDialog(it, selectItems).apply {
                    this.selectEvent = selectEvent
                    show()
                }
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

fun convertToMultipart(
    params: LinkedHashMap<String, Any>,
    imageName: String
): Pair<LinkedHashMap<String, RequestBody>, List<MultipartBody.Part>> {
    val params2 = LinkedHashMap<String, RequestBody>()
    val images = mutableListOf<MultipartBody.Part>()

    for (key in params.keys) {
        params[key]?.also { value ->
            if (key != "images") {
                params2[key] = value.toString().toRequestBody("text/plain".toMediaTypeOrNull())
            } else {
                if (value is List<*>) {
                    for (image in value) {
                        val imagePath = image as String
                        val file = File(imagePath)
                        val fileReqBody = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
                        val part = MultipartBody.Part.createFormData(
                            imageName,
                            file.name,
                            fileReqBody
                        )
                        images.add(part)
                    }
                }
            }
        }
    }
    return Pair(params2, images)
}

/**
 * 스크롤 아래로 퍼센트 계산
 * @return %
 */
fun scrollPercent(recyclerView: RecyclerView): Double {
    return (recyclerView.computeVerticalScrollOffset() * 1.0 / (recyclerView.computeVerticalScrollRange() - recyclerView.computeVerticalScrollExtent())) * 100.0
}