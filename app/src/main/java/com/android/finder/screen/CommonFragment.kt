package com.android.finder.screen

import android.app.Dialog
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.android.finder.R
import com.android.finder.viewmodel.NavigationViewModel

abstract class CommonFragment<T : ViewDataBinding>(@LayoutRes private val layoutId: Int) :
    Fragment() ,EventListenerSetting {
    private var _binding: T? = null
    protected val binding: T get() = _binding!!
    protected val navController: NavController get() = findNavController()
    lateinit var progressDialog: Dialog

    var isLoading: Boolean = false
        set(value) {
            field = value
            if (value) progressDialog.show()
            else progressDialog.dismiss()
        }

    private val navigationViewModel : NavigationViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(
            layoutInflater,
            layoutId,
            null,
            false
        )
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        progressDialog = Dialog(requireContext())
        progressDialog.setContentView(R.layout.dialog_progress)
        progressDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        progressDialog.setCancelable(false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        eventListenerSetting()
        navigationAction()
    }

    @CallSuper
    override fun onPause() {
        super.onPause()
        navigationClear()
    }

    @CallSuper
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun navigationAction() {
        navigationViewModel.apply {
            navDirectionAction.observe(viewLifecycleOwner) {
                if (it != null) navController.navigate(it)
            }
            popBackStack.observe(viewLifecycleOwner) {
                if (it) navController.popBackStack()
            }
        }
    }

    private fun navigationClear() {
        navigationViewModel.apply {
            navDirectionAction.value = null
        }
    }

    fun navPopStack() { navigationViewModel.popBackStack.postValue(true) }

    fun navigate(direction: NavDirections) { navigationViewModel.navDirectionAction.postValue(direction) }

    fun getFullPathFromUri(ctx: Context, fileUri: Uri?): String? {
        var fullPath: String? = null
        val column = "_data"
        var cursor = ctx.contentResolver.query(fileUri!!, null, null, null, null)
        if (cursor != null) {
            cursor.moveToFirst()
            var documentId = cursor.getString(0)
            if (documentId == null) {
                for (i in 0 until cursor.columnCount) {
                    if (column.equals(cursor.getColumnName(i), ignoreCase = true)) {
                        fullPath = cursor.getString(i)
                        break
                    }
                }
            } else {
                documentId = documentId.substring(documentId.lastIndexOf(":") + 1)
                cursor.close()
                val projection = arrayOf(column)
                try {
                    cursor = ctx.contentResolver.query(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        projection,
                        MediaStore.Images.Media._ID + " = ? ",
                        arrayOf(documentId),
                        null
                    )
                    if (cursor != null) {
                        cursor.moveToFirst()
                        fullPath = cursor.getString(cursor.getColumnIndexOrThrow(column))
                    }
                } finally {
                    cursor.close()
                }
            }
        }
        return fullPath
    }
}

interface EventListenerSetting {

    fun eventListenerSetting()
}