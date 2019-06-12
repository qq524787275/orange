package com.zhuzichu.mvvm.utils

import android.os.Environment

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-06-05
 * Time: 12:56
 */

fun isSDCardMounted(): Boolean {
    return Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
}
