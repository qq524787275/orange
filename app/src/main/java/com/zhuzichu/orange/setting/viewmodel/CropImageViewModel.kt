package com.zhuzichu.orange.setting.viewmodel

import android.app.Application
import com.zhuzichu.mvvm.base.BaseViewModel
import com.zhuzichu.mvvm.bus.event.SingleLiveEvent
import com.zhuzichu.mvvm.databinding.command.BindingCommand
import com.zhuzichu.orange.setting.fragment.CropImageFragment

class CropImageViewModel(application: Application) : BaseViewModel(application) {
    val onClickSubmitEvent = SingleLiveEvent<Boolean>()

    val onClickSubmit = BindingCommand<Any>({
        onClickSubmitEvent.call()
    })

    val onClickExit = BindingCommand<Any>({
        back()
    })
}