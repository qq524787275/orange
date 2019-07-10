package com.zhuzichu.mvvm.databinding.checkbox

import android.widget.CheckBox
import androidx.annotation.NonNull
import androidx.databinding.BindingAdapter
import com.zhuzichu.mvvm.databinding.command.BindingCommand


/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-07-10
 * Time: 11:15
 */

@BindingAdapter(value = ["onCheckedChangedCommand"], requireAll = false)
fun setCheckedChanged(checkBox: CheckBox, bindingCommand: BindingCommand<Boolean>) {
    checkBox.setOnCheckedChangeListener { _, b -> bindingCommand.execute(b) }
}

@BindingAdapter("isChecked")
fun isChecked(checkBox: CheckBox, @NonNull isChecked: Boolean? = null) {
    if (isChecked != null)
        checkBox.isChecked = isChecked
}