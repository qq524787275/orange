package com.zhuzichu.mvvm.databinding.chip

import androidx.databinding.BindingAdapter
import com.google.android.material.chip.Chip
import com.zhuzichu.mvvm.databinding.command.BindingCommand

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-06-28
 * Time: 11:28
 */
@BindingAdapter("onCloseIconCommand")
fun onCloseIconCommand(chip: Chip, onCloseIconCommand: BindingCommand<Any>? = null) {
    chip.setOnCloseIconClickListener {
        onCloseIconCommand?.execute()
    }
}