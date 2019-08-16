package com.zhuzichu.orange.main.fragment

import android.annotation.SuppressLint
import android.content.Intent
import androidx.core.os.bundleOf
import com.jakewharton.rxbinding3.view.clicks
import com.liulishuo.okdownload.DownloadListener
import com.liulishuo.okdownload.DownloadTask
import com.liulishuo.okdownload.StatusUtil
import com.liulishuo.okdownload.UnifiedListenerManager
import com.zhuzichu.mvvm.base.BaseTopbarBackFragment
import com.zhuzichu.mvvm.bean.VersionBean
import com.zhuzichu.mvvm.global.cache.CacheGlobal
import com.zhuzichu.mvvm.utils.FileProvider7
import com.zhuzichu.mvvm.utils.MimeUtils
import com.zhuzichu.mvvm.utils.bindArgument
import com.zhuzichu.orange.BR
import com.zhuzichu.orange.R
import com.zhuzichu.orange.databinding.FragmentUpdateBinding
import com.zhuzichu.orange.main.viewmodel.UpdateViewModel
import com.zhuzichu.orange.ui.file.FileActivity
import com.zhuzichu.orange.widget.NotificationDownListener
import kotlinx.android.synthetic.main.fragment_update.*
import java.io.File


class UpdateFragment : BaseTopbarBackFragment<FragmentUpdateBinding, UpdateViewModel>() {

    companion object {
        const val VERSION_INFO = "VERSION_INFO"
    }

    private val info: VersionBean.Info by bindArgument(VERSION_INFO)

    override fun setLayoutId(): Int = R.layout.fragment_update

    override fun bindVariableId(): Int = BR.viewModel

    private lateinit var listener: NotificationDownListener

    private lateinit var task: DownloadTask

    fun installApk() {
        val file = File(
            CacheGlobal.getDownLoadCacheDir(),
            "app-release.apk"
        )
        val intent = Intent(Intent.ACTION_VIEW)
        FileProvider7.setIntentDataAndType(
            requireContext(),
            intent, "application/vnd.android.package-archive", file, true
        )
        startActivity(intent)
    }

    @SuppressLint("CheckResult")
    override fun initView() {
        setTitle("版本更新")
        addRightIcon(R.drawable.ic_file) {
            _viewModel.startActivity(
                FileActivity::class.java, bundle = bundleOf(
                    FileActivity.PATH to CacheGlobal.getDownLoadCacheDir().absolutePath
                )
            )
        }

        _viewModel.fileName.value = MimeUtils.getFileNameFormUrl(info.url)
        _viewModel.versionName.value = "V".plus(info.versionName)


        startBtn.clicks().subscribe {
            if (startBtn.text == "下载") {
                GlobalTaskManager.impl.enqueueTask(task, listener)
                startBtn.text = "取消"
            } else {
                task.cancel()
            }
        }

        initListener()
        initTask()
        GlobalTaskManager.impl.attachListener(task, listener)
        GlobalTaskManager.impl.addAutoRemoveListenersWhenTaskEnd(task.id)

        startBtn.text = "下载"
        if (StatusUtil.isSameTaskPendingOrRunning(task)) {
            startBtn.text = "取消"
        }
    }

    private fun initListener() {
        listener = NotificationDownListener(requireContext())
    }

    private fun initTask() {

        task = DownloadTask.Builder(info.url, CacheGlobal.getDownLoadCacheDir())
            .setFilename(MimeUtils.getFileNameFormUrl(info.url))
            .setPassIfAlreadyCompleted(false)
            .setMinIntervalMillisCallbackProcess(80)
            .setAutoCallbackToUIThread(false)
            .build()
    }

    internal class GlobalTaskManager private constructor() {
        private val manager: UnifiedListenerManager = UnifiedListenerManager()

        private object ClassHolder {
            val INSTANCE = GlobalTaskManager()
        }

        fun addAutoRemoveListenersWhenTaskEnd(id: Int) {
            manager.addAutoRemoveListenersWhenTaskEnd(id)
        }

        fun attachListener(task: DownloadTask, listener: DownloadListener) {
            manager.attachListener(task, listener)
        }

        fun enqueueTask(
            task: DownloadTask,
            listener: DownloadListener
        ) {
            manager.enqueueTaskWithUnifiedListener(task, listener)
        }

        companion object {

            val impl: GlobalTaskManager
                get() = ClassHolder.INSTANCE
        }
    }
}
