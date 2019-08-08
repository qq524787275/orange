package com.zhuzichu.mvvm.bean

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-08-08
 * Time: 18:32
 */
data class CategoryBean(
    var id: Long = 0L,
    var name: String = "",
    var image: String = "",
    var pid: Long = 0L,
    var childs: MutableList<CategoryBean> = mutableListOf()
)