package com.zhuzichu.mvvm.base

import android.os.Bundle

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-05-27
 * Time: 16:59
 */
interface IBaseFragment {
    fun onEnterAnimationEnd(savedInstanceState: Bundle?) {}

    fun initViewObservable() {}

    fun initVariable() {}

    fun initView() {}
}