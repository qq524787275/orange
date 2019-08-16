package com.zhuzichu.orange

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.zhuzichu.mvvm.utils.logi

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-08-16
 * Time: 11:17
 */
class DownLoaderWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    override fun doWork(): Result {
        val list = listOf("执行了1", "执行了2", "执行了3", "执行了4", "执行了5")
        list.forEach {
            Thread.sleep(3000)
            it.logi()
        }
        return Result.success()
    }
}