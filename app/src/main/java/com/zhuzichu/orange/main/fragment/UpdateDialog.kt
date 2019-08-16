package com.zhuzichu.orange.main.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.widget.TextView
import com.jakewharton.rxbinding3.view.clicks
import com.zhuzichu.mvvm.base.BaseDialog
import com.zhuzichu.mvvm.utils.toast
import com.zhuzichu.orange.R


class UpdateDialog(context: Context) : BaseDialog(context, R.layout.dialog_update) {
    @SuppressLint("CheckResult")
    override fun initView() {
        findViewById<TextView>(R.id.left)?.clicks()?.subscribe {
            cancel()
        }

        findViewById<TextView>(R.id.right)?.clicks()?.subscribe {
            "去下载".toast()
            cancel()
        }
    }
}