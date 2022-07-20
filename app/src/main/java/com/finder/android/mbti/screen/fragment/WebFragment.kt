package com.finder.android.mbti.screen.fragment

import android.app.Dialog
import android.os.Bundle
import android.os.Message
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.navigation.fragment.navArgs
import com.finder.android.mbti.R
import com.finder.android.mbti.databinding.FragmentWebBinding
import com.finder.android.mbti.CommonFragment

class WebFragment : CommonFragment<FragmentWebBinding>(R.layout.fragment_web) {
    private val args: WebFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.e("url", args.url)
    }

    override fun onStart() {
        super.onStart()

//    binding.closeIb.setOnClickListener { navController.popBackStack() }
        binding.webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                view.loadUrl(url)
                return false
            }
        }
        binding.webView.settings.javaScriptEnabled = true
        binding.webView.settings.domStorageEnabled = true
        binding.webView.settings.javaScriptCanOpenWindowsAutomatically = true
        binding.webView.settings.setSupportMultipleWindows(true)
        binding.webView.requestFocusFromTouch()

        binding.webView.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN) {
                return@setOnKeyListener true
            }
            if(keyCode == KeyEvent.KEYCODE_BACK) {
                if(binding.webView.canGoBack()) {
                    binding.webView.goBack()
                    return@setOnKeyListener true
                } else {
                    requireActivity().onBackPressed()
                    return@setOnKeyListener false
                }
            }
            return@setOnKeyListener false
        }
        binding.webView.webChromeClient = object : WebChromeClient() {
            override fun onCreateWindow(
                view: WebView?,
                isDialog: Boolean,
                isUserGesture: Boolean,
                resultMsg: Message
            ): Boolean {
                val newWebView = WebView(context!!)
                val webSettings = newWebView.settings
                webSettings.javaScriptEnabled = true
                webSettings.domStorageEnabled = true
                val dialog = Dialog(context!!)
                dialog.setContentView(newWebView)
                val params: ViewGroup.LayoutParams = dialog.window!!.attributes
                params.width = ViewGroup.LayoutParams.MATCH_PARENT
                params.height = ViewGroup.LayoutParams.MATCH_PARENT
                dialog.window!!.attributes = params as WindowManager.LayoutParams
                dialog.show()
                newWebView.webChromeClient = object : WebChromeClient() {
                    override fun onCloseWindow(window: WebView) {
                        dialog.dismiss()
                    }
                }

                newWebView.webViewClient = object : WebViewClient() {
                    override fun shouldOverrideUrlLoading(
                        view: WebView,
                        request: WebResourceRequest
                    ): Boolean {
                        return false
                    }
                }

                (resultMsg.obj as WebView.WebViewTransport).webView = newWebView
                resultMsg.sendToTarget()
                return true
            }
        }
        binding.webView.loadUrl(args.url)
    }

    override fun eventListenerSetting() { }
}