package com.zhuzichu.orange.bean

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-06-12
 * Time: 17:27
 */

data class SortBean(
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
