package com.zhuzichu.mvvm.databinding.edittext

import android.text.InputType
import android.widget.EditText
import androidx.databinding.BindingAdapter

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-06-17
 * Time: 14:23
 */

@BindingAdapter(value = ["disableInput"], requireAll = false)
fun disableInput(editText: EditText, disableInput: Boolean=false) {
    if(disableInput){
        editText.inputType = InputType.TYPE_NULL;//来禁止手机软键盘
    }else{
        editText.inputType = InputType.TYPE_CLASS_TEXT;//来开启软键盘
    }
}