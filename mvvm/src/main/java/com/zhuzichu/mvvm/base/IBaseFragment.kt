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

    //第一次fragment可见（进行初始化工作）
    fun onFirstUserVisible() {}

    //fragment可见（切换回来或者onResume）
    fun onUserVisible() {}

    //第一次fragment不可见（不建议在此处理事件）
    fun onFirstUserInvisible() {}

    //fragment不可见（切换掉或者onPause）
    fun onUserInvisible() {}
}