package com.zhuzichu.mvvm.bean

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-07-31
 * Time: 18:27
 */
data class HeaderBean(
    var platform: String? = null,
    var device: String? = null,
    var versionCode: Int? = null,
    var versionName: String? = null
)