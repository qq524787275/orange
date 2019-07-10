package com.zhuzichu.orange.main.viewmodel

import android.app.Application
import com.zhuzichu.mvvm.base.BaseViewModel
import com.zhuzichu.mvvm.global.color.ColorGlobal

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-06-12
 * Time: 15:21
 */
class MainViewModel(application: Application) : BaseViewModel(application) {
    val color = ColorGlobal
}