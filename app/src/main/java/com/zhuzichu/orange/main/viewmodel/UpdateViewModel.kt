package com.zhuzichu.orange.main.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.zhuzichu.mvvm.base.BaseViewModel
import com.zhuzichu.mvvm.global.color.ColorGlobal

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-08-13
 * Time: 14:55
 */
class UpdateViewModel(application: Application) : BaseViewModel(application) {
    val color = ColorGlobal
    val fileName = MutableLiveData<String>()
    val versionName = MutableLiveData<String>()
}