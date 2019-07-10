package com.zhuzichu.orange.find.viewmodel

import android.app.Application
import com.zhuzichu.mvvm.base.BaseViewModel
import com.zhuzichu.mvvm.global.color.ColorGlobal

class FindViewModel(application: Application) : BaseViewModel(application){
    val color=ColorGlobal
}