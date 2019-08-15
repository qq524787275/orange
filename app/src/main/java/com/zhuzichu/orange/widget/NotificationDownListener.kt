package com.zhuzichu.orange.widget

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import androidx.core.app.NotificationCompat
import com.liulishuo.okdownload.DownloadTask
import com.liulishuo.okdownload.SpeedCalculator
import com.liulishuo.okdownload.core.breakpoint.BlockInfo
import com.liulishuo.okdownload.core.breakpoint.BreakpointInfo
import com.liulishuo.okdownload.core.cause.EndCause
import com.liulishuo.okdownload.core.cause.EndCause.*
import com.liulishuo.okdownload.core.listener.DownloadListener4WithSpeed
import com.liulishuo.okdownload.core.listener.assist.Listener4SpeedAssistExtend
import com.zhuzichu.mvvm.notify.Notify
import com.zhuzichu.mvvm.utils.toast


/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-08-15
 * Time: 10:13
 */
class NotificationDownListener(context: Context) : DownloadListener4WithSpeed() {
    private var context: Context = context.applicationContext
    private var totalLength: Int = 100
    private var totalOffset: Int = 0

    override fun taskStart(task: DownloadTask) {
        Notify.with(context)
            .content {
                title = "开始下载"
            }
            .progress {
                max = 0
                progress = 0
            }
            .show(task.id)
    }

    override fun blockEnd(task: DownloadTask, blockIndex: Int, info: BlockInfo?, blockSpeed: SpeedCalculator) {
    }

    override fun taskEnd(task: DownloadTask, cause: EndCause, realCause: Exception?, taskSpeed: SpeedCalculator) {

        when (cause) {
            COMPLETED -> {
                Notify.with(context)
                    .content {
                        title = "下载完成"
                        text = "平均速度:".plus(taskSpeed.averageSpeed())
                    }
                    .actions {
                        Handler(Looper.getMainLooper()).post {
                            task.file?.absolutePath?.toast()
                        }

                        val contentIntent =
                            PendingIntent.getActivity(context, 0,Intent(), PendingIntent.FLAG_UPDATE_CURRENT)
                        add(NotificationCompat.Action.Builder(0,"安装",contentIntent).build())
                    }
                    .show(task.id)
            }
            ERROR -> TODO()
            CANCELED -> TODO()
            FILE_BUSY -> TODO()
            SAME_TASK_BUSY -> TODO()
            PRE_ALLOCATE_FAILED -> TODO()
        }
    }

    override fun progress(task: DownloadTask, currentOffset: Long, taskSpeed: SpeedCalculator) {
        totalOffset = currentOffset.toInt()
        Notify.with(context)
            .content {
                title = "正在下载"
                text = "下载速度:".plus(taskSpeed.speed())
            }
            .progress {
                max = totalLength
                progress = totalOffset
            }
            .show(task.id)
    }

    override fun connectEnd(
        task: DownloadTask,
        blockIndex: Int,
        responseCode: Int,
        responseHeaderFields: MutableMap<String, MutableList<String>>
    ) {
    }

    override fun connectStart(
        task: DownloadTask,
        blockIndex: Int,
        requestHeaderFields: MutableMap<String, MutableList<String>>
    ) {
    }

    override fun infoReady(
        task: DownloadTask,
        info: BreakpointInfo,
        fromBreakpoint: Boolean,
        model: Listener4SpeedAssistExtend.Listener4SpeedModel
    ) {
        totalLength = info.totalLength.toInt()
        totalOffset = info.totalOffset.toInt()

        Notify.with(context)
            .content {
                title = "正在下载"
            }
            .progress {
                max = totalLength
                progress = totalOffset
            }
            .show(task.id)
    }

    override fun progressBlock(
        task: DownloadTask,
        blockIndex: Int,
        currentBlockOffset: Long,
        blockSpeed: SpeedCalculator
    ) {
    }
}