package com.zhuzichu.mvvm.base

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-05-27
 * Time: 15:19
 */

interface IBaseViewModel : LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    fun onAny(owner: LifecycleOwner, event: Lifecycle.Event){}

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate(){}

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy(){}

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart(){}

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop(){}

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume(){}

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause(){}

    fun onEnterAnimationEnd(){}

    //第一次fragment可见（进行初始化工作）
    fun onFirstUserVisible() {}

    //fragment可见（切换回来或者onResume）
    fun onUserVisible() {}

    //fragment不可见（切换掉或者onPause）
    fun onUserInvisible() {}
}