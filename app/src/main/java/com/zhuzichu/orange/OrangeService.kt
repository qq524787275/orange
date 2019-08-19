package com.zhuzichu.orange

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.os.IBinder
import android.os.Process
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.Builder
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
        private val NOTIFICATION_ID = Process.myPid()
        const val TAG = "OrangeService"
    }

    override fun onCreate() {
        super.onCreate()
        startForeground(NOTIFICATION_ID, getNotification())
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
                Thread.sleep(4000)
                "执行了".plus(it).logi(TAG)
            }
        }.start()
        stopForeground(true)
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

    private fun getNotification(): Notification {
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelId = "orange"
        if (VERSION.SDK_INT >= VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "orange_service",
                NotificationManager.IMPORTANCE_MIN
            )
            manager.createNotificationChannel(channel)
        }
        val builder = Builder(this, channelId)
        builder.setDefaults(Notification.DEFAULT_ALL)
            .setOngoing(true).setOnlyAlertOnce(true)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setContentTitle("服务运行于前台")
            .setContentText("service正在后台运行...")
            .setSmallIcon(R.mipmap.ic_launcher)
        return builder.build()
    }
}