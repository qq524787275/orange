package com.zhuzichu.orange.main.fragment

import android.annotation.SuppressLint
import android.content.Intent
import com.jakewharton.rxbinding3.view.clicks
import com.zhuzichu.mvvm.base.BaseTopbarBackFragment
import com.zhuzichu.mvvm.bean.VersionBean
import com.zhuzichu.mvvm.downloader.Downloader
import com.zhuzichu.mvvm.downloader.core.OnDownloadListener
import com.zhuzichu.mvvm.global.cache.CacheGlobal
import com.zhuzichu.mvvm.utils.*
import com.zhuzichu.orange.BR
import com.zhuzichu.orange.R
import com.zhuzichu.orange.databinding.FragmentUpdateBinding
import com.zhuzichu.orange.main.viewmodel.UpdateViewModel
import kotlinx.android.synthetic.main.fragment_update.*
import java.io.File


class UpdateFragment : BaseTopbarBackFragment<FragmentUpdateBinding, UpdateViewModel>() {

    private lateinit var downloader: Downloader

    companion object {
        const val VERSION_INFO = "VERSION_INFO"
    }

    private val info: VersionBean.Info by bindArgument(VERSION_INFO)

    override fun setLayoutId(): Int = R.layout.fragment_update

    override fun bindVariableId(): Int = BR.viewModel


    fun installApk() {
        val file = File(
            CacheGlobal.getDownLoadCacheDir(),
            "app-release.apk"
        )
        val intent = Intent(Intent.ACTION_VIEW)
        FileProvider7.setIntentDataAndType(
            _activity,
            intent, "application/vnd.android.package-archive", file, true
        )
        startActivity(intent)
    }

    @SuppressLint("CheckResult")
    override fun initView() {
        setTitle("版本更新")
        addRightIcon(R.drawable.ic_file) {
            val parentFlie = CacheGlobal.getDownLoadCacheDir()
            val path = parentFlie.absolutePath
            path.length.logi("zzc")
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            FileProvider7.setIntentData(requireContext(), intent, parentFlie, true)
            startActivityForResult(intent, 111)
        }

        _viewModel.fileName.value = MimeUtils.getFileNameFormUrl(info.url)
        _viewModel.versionName.value = "V".plus(info.versionName)

        downloader = Downloader.Builder(requireContext(), info.url)
            .downloadDirectory(CacheGlobal.getDownLoadCacheDir().path)
            .downloadListener(object : OnDownloadListener {
                override fun onStart() {
                    "onStart".logi()
                }

                override fun onPause() {
                    "onPause".logi()
                }

                override fun onResume() {
                    "onResume".logi()
                }

                override fun onProgressUpdate(percent: Int, downloadedSize: Int, totalSize: Int) {
                    "onProgressUpdate".logi()
                    progress.progress = percent
                }

                override fun onCompleted(file: File?) {
                    "onCompleted".logi()
                }

                override fun onFailure(reason: String?) {
                    "onFailure".logi()
                }

                override fun onCancel() {
                    "onCancel".logi()
                }

            })
            .build()

        startBtn.clicks().subscribe {
            "出发了".toast()
            downloader.download()
        }

        pauseBtn.clicks().subscribe {
            downloader.pauseDownload()
        }
    }
}
