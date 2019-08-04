package com.zhuzichu.orange.setting.viewmodel

import android.app.Application
import android.text.Editable
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.zhuzichu.mvvm.base.BaseViewModel
import com.zhuzichu.mvvm.bus.event.SingleLiveEvent
import com.zhuzichu.mvvm.databinding.command.BindingCommand
import com.zhuzichu.mvvm.global.color.ColorGlobal

class EditItemViewModel(application: Application) : BaseViewModel(application) {
    val color = ColorGlobal

    val text = ObservableField<String>()
    val tips = MutableLiveData<String>()
    val isEnableSure = ObservableBoolean()

    val onSureEvent = SingleLiveEvent<String>()

    val onClickSure = BindingCommand<Any>({
        if (isEnableSure.get()) {
            onSureEvent.value = text.get()
        }
    })

}