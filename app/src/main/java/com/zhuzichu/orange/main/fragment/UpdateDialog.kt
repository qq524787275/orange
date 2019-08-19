package com.zhuzichu.orange.main.fragment

import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.core.text.HtmlCompat
import com.afollestad.materialdialogs.MaterialDialog
import com.jakewharton.rxbinding3.view.clicks
import com.zhuzichu.mvvm.base.BaseDialog
import com.zhuzichu.mvvm.bean.VersionBean
import com.zhuzichu.mvvm.global.cache.CacheGlobal
import com.zhuzichu.mvvm.utils.*
import com.zhuzichu.orange.R
import java.io.File


class UpdateDialog(
    context: Context,
    private val info: VersionBean.Info
) : BaseDialog(context, R.layout.dialog_update) {
    @SuppressLint("CheckResult")
    override fun initView() {
        val layoutParams = findViewById<CardView>(R.id.card)?.layoutParams
        layoutParams?.height = (getScreenH() * 0.6).toInt()
        layoutParams?.width = (getScreenW() * 0.8).toInt()

        findViewById<TextView>(R.id.left)?.clicks()?.subscribe {
            cancel()
        }

        findViewById<TextView>(R.id.right)?.clicks()?.subscribe {
            downLoadApk(info.url)
            cancel()
        }

        findViewById<TextView>(R.id.text)?.text =
            HtmlCompat.fromHtml(info.content, HtmlCompat.FROM_HTML_MODE_COMPACT)
    }


    private fun downLoadApk(url: String) {
        val path = CacheGlobal.getDownLoadCacheDir().absolutePath.plus(File.separator).plus(
            MimeUtils.getFileNameFormUrl(
                url
            )
        )
        val file = File(path)
        if (file.exists()) {
            FileUtils.delAllFile(file)
        }
        val request = DownloadManager.Request(Uri.parse(url))
        request.setTitle("下载")
        request.setDescription("橙子街正在下载...")
        request.setAllowedOverRoaming(false)
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        request.setDestinationUri(file.toUri())
        val downloadManager = ContextCompat.getSystemService(context, DownloadManager::class.java)

        if (NetUtils.isWifiConnected(context)) {
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI)
            downloadManager?.let {
                val id = it.enqueue(request)
                "出发了:".plus(id).toast()
            }
        } else {
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE)
            MaterialDialog(context).show {
                title(text = "友情提示")
                message(text = "当前不是在wifi环境中，确定要下载么?")
                positiveButton(text = "确定") {
                    downloadManager?.enqueue(request)
                }
                negativeButton(text = "取消")
            }
        }
    }
}