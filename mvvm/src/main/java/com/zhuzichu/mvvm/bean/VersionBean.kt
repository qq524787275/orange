package com.zhuzichu.mvvm.bean

import java.io.Serializable

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-08-13
 * Time: 11:30
 */
data class VersionBean(
    var info: Info = Info(),
    var isUpdate: Boolean = false
) {
    data class Info(
        var id: Int = 0,
        var platform: String = "",
        var url: String = "",
        var versionCode: Int = 0,
        var versionName: String = ""
    ) : Serializable
}