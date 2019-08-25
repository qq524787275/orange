package com.zhuzichu.mvvm.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient

object WebUtils {
    @SuppressLint("ObsoleteSdkInt", "SetJavaScriptEnabled")
    fun initWebView(context: Context, webBase: WebView) {
        val webSettings: WebSettings = webBase.settings
        if (Build.VERSION.SDK_INT >= 19) {
            webSettings.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK//加载缓存否则网络
        }
        webSettings.loadsImagesAutomatically = Build.VERSION.SDK_INT >= 19
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            webBase.setLayerType(View.LAYER_TYPE_SOFTWARE, null)//软件解码
        }
        webBase.setLayerType(View.LAYER_TYPE_HARDWARE, null)//硬件解码
        webSettings.javaScriptEnabled = true // 设置支持javascript脚本
        webSettings.setSupportZoom(true)// 设置可以支持缩放
        webSettings.builtInZoomControls = true// 设置出现缩放工具 是否使用WebView内置的缩放组件，由浮动在窗口上的缩放控制和手势缩放控制组成，默认false
        webSettings.displayZoomControls = false//隐藏缩放工具
        webSettings.useWideViewPort = true// 扩大比例的缩放
        webSettings.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN//自适应屏幕
        webSettings.loadWithOverviewMode = true
        webSettings.databaseEnabled = true//
        webSettings.setSavePassword(true)//保存密码
        webSettings.domStorageEnabled =
            true//是否开启本地DOM存储  鉴于它的安全特性（任何人都能读取到它，尽管有相应的限制，将敏感数据存储在这里依然不是明智之举），Android 默认是关闭该功能的。
        webBase.isSaveEnabled = true
        webBase.keepScreenOn = true


        webBase.setDownloadListener { paramAnonymousString1, _, _, _, _ ->
            val intent = Intent()
            intent.action = "android.intent.action.VIEW"
            intent.data = Uri.parse(paramAnonymousString1)
            context.startActivity(intent)
        }
    }

    fun loadData(webView: WebView, content: String?) {
        webView.loadDataWithBaseURL(null, content, "text/html", "UTF-8", null)//这种写法可以正确解码
    }
}