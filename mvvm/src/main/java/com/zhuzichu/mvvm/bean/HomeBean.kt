package com.zhuzichu.mvvm.bean

data class HomeBean(
    val id: Long,
    val name: String,
    val pageSize: Long,
    val showType: Int,
    val list: List<GoodsBean> = listOf()
)