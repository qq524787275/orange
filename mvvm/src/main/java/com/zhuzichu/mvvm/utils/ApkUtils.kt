package com.zhuzichu.mvvm.utils

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

