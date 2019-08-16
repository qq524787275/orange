package com.zhuzichu.orange.main.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.jakewharton.rxbinding3.view.clicks
import com.zhuzichu.mvvm.base.BaseDialog
import com.zhuzichu.mvvm.utils.getScreenH
import com.zhuzichu.mvvm.utils.getScreenW
import com.zhuzichu.mvvm.utils.toast
import com.zhuzichu.orange.DownLoaderWorker
import com.zhuzichu.orange.R


class UpdateDialog(context: Context) : BaseDialog(context, R.layout.dialog_update) {
    @SuppressLint("CheckResult")
    override fun initView() {
        val layoutParams = findViewById<CardView>(R.id.card)?.layoutParams
        layoutParams?.height = (getScreenH() * 0.8).toInt()
        layoutParams?.width = (getScreenW() * 0.8).toInt()

        findViewById<TextView>(R.id.left)?.clicks()?.subscribe {
            cancel()
        }

        findViewById<TextView>(R.id.right)?.clicks()?.subscribe {
            "去下载".toast()
            val uploadWorkRequest = OneTimeWorkRequestBuilder<DownLoaderWorker>()
                .build()
            WorkManager.getInstance().enqueue(uploadWorkRequest)
            cancel()
        }
    }
}