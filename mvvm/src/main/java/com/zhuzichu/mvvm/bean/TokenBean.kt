package com.zhuzichu.mvvm.bean

data class TokenBean(
    var token: String = "",
    var userInfo: UserInfoBean = UserInfoBean()
)