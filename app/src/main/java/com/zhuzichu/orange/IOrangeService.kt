package com.zhuzichu.orange

import com.zhuzichu.mvvm.base.OkBinder

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-08-16
 * Time: 15:35
 */

@OkBinder.Interface
interface IOrangeService {
    fun getTestString(): String

    fun stopService()
}