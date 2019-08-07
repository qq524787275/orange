package com.zhuzichu.mvvm.databinding.edittext

import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.widget.EditText
import androidx.annotation.NonNull
import androidx.databinding.BindingAdapter
import com.zhuzichu.mvvm.databinding.command.BindingCommand


/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-06-17
 * Time: 14:23
 */

@BindingAdapter(value = ["disableInput"], requireAll = false)
fun disableInput(editText: EditText, disableInput: Boolean = false) {
    if (disableInput) {
        editText.inputType = InputType.TYPE_NULL//来禁止手机软键盘
    } else {
        editText.inputType = InputType.TYPE_CLASS_TEXT//来开启软键盘
    }
}

@BindingAdapter(value = ["textColorHint"], requireAll = false)
fun bindEditText(editText: EditText, @NonNull textColorHint: Int? = null) {
    textColorHint?.let {
        editText.setHintTextColor(it)
    }
}

@BindingAdapter(value = ["onAfterTextCommand"], requireAll = false)
fun bindTextChanged(editText: EditText, onAfterTextCommand: BindingCommand<Editable>) {
    editText.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable) {
            onAfterTextCommand.execute(s)
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
        }
    })
}
