package com.zhuzichu.orange.setting.viewmodel

import android.app.Application
import com.zhuzichu.mvvm.base.BaseViewModel
import com.zhuzichu.mvvm.global.AppGlobal
import com.zhuzichu.mvvm.global.color.ColorGlobal

class EditAvatarViewModel(application: Application) : BaseViewModel(application) {
    val global = AppGlobal
    val color = ColorGlobal
}