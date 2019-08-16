package com.zhuzichu.mvvm.utils

import android.app.ActivityManager
import android.content.Context
import com.ali.auth.third.core.context.KernelContext.getApplicationContext
import com.zhuzichu.mvvm.global.AppGlobal

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-07-31
 * Time: 18:33
 */

@Suppress("DEPRECATION")
fun getVersionCode(): Int {
    return try {
        AppGlobal.context.packageManager.getPackageInfo(AppGlobal.context.packageName, 0).versionCode
    } catch (e: Exception) {
        0
    }
}

fun getVersionName(): String {
    return try {
        AppGlobal.context.packageManager.getPackageInfo(AppGlobal.context.packageName, 0).versionName
    } catch (e: Exception) {
        ""
    }
}


fun isAppMainProcess(): Boolean {
    val pid = android.os.Process.myPid()
    val processName = getProcessNameByPid(pid)
    if (processName == getApplicationContext().packageName)
        return true
    return false
}

fun getProcessNameByPid(pid: Int): String {
    val manager = AppGlobal.context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
    manager.runningAppProcesses.forEach {
        if (it.pid == pid)
            return it.processName
    }
    return ""
}

