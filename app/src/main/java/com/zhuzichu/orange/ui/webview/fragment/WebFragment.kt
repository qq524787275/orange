package com.zhuzichu.orange.ui.webview.fragment

import android.annotation.SuppressLint
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import com.zhuzichu.mvvm.base.BaseTopbarBackFragment
import com.zhuzichu.mvvm.utils.*
import com.zhuzichu.orange.BR
import com.zhuzichu.orange.R
import com.zhuzichu.orange.databinding.FragmentWebBinding
import com.zhuzichu.orange.ui.webview.viewmodel.WebViewModel
import com.zhuzichu.orange.utils.showTradeUrl
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_web.*

class WebFragment : BaseTopbarBackFragment<FragmentWebBinding, WebViewModel>() {
    companion object {
        const val URL = "URL"
    }

    private val disposables = CompositeDisposable()
    private val url: String by bindArgument(URL)

    override fun setLayoutId(): Int = R.layout.fragment_web

    override fun bindVariableId(): Int = BR.viewModel

    @SuppressLint("CheckResult")
    override fun initView() {
        setTitle("...")

        WebUtils.initWebView(requireContext(), webview)
        webview.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                if (!webview.settings.loadsImagesAutomatically) {
                    webview.settings.loadsImagesAutomatically = true
                }
            }

            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                val uri = url.split("?")[0]
                if (uri == "https://uland.taobao.com/coupon/edetail") {
                    showTradeUrl(_activity, url)
                    return true
                }
                return false
            }
        }
        webview.webChromeClient = object : WebChromeClient() {
            override fun onReceivedTitle(view: WebView, title: String) {
                setTitle(title)
            }
        }

        webview.loadUrl(url)
    }

    override fun onBackPressedSupport(): Boolean {
        if (webview.canGoBack()) {
            webview.goBack()
            return true
        }
        return super.onBackPressedSupport()
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.clear()
    }
}