package com.zhuzichu.orange

import android.annotation.SuppressLint
import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.zhuzichu.mvvm.base.OkBinder
import com.zhuzichu.mvvm.utils.logi

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-08-16
 * Time: 12:39
 */
class OrangeService : Service() {

    companion object {
        const val TAG = "OrangeService"
    }

    override fun onCreate() {
        super.onCreate()
        "onCreate".logi(TAG)
    }

    override fun onRebind(intent: Intent?) {
        super.onRebind(intent)
        "onRebind".logi(TAG)
    }

    override fun onUnbind(intent: Intent?): Boolean {
        "onUnbind".logi(TAG)
        return super.onUnbind(intent)
    }

    @SuppressLint("WrongConstant")
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        "onStartCommand".logi(TAG)
        Thread {
            val list = listOf("1", "2", "3", "4")
            list.forEach {
                Thread.sleep(3000)
                "执行了".plus(it).logi(TAG)
            }
        }.start()
        return super.onStartCommand(intent, START_STICKY_COMPATIBILITY, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        "onDestroy".logi(TAG)
    }

    private val okBinder = OkBinder(object : IOrangeService {
        override fun stopService() {
            stopSelf()
        }

        override fun getTestString(): String {

            return "远程服务数据"
        }

    })


    override fun onBind(intent: Intent?): IBinder? {
        "onBind".logi(TAG)
        return okBinder
    }
}