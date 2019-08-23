package com.zhuzichu.mvvm.bean

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-08-08
 * Time: 18:32
 */
data class CategoryBean(
    var `data`: List<Data> = listOf(),
    var cid: Int = 0,
    var main_name: String = ""
) {
    data class Data(
        var info: List<Info> = listOf(),
        var next_name: String = ""
    ) {
        data class Info(
            var imgurl: String = "",
            var son_name: String = ""
        )
    }
}