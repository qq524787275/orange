package com.zhuzichu.mvvm.databinding.layout

import androidx.annotation.NonNull
import androidx.databinding.BindingAdapter
import com.zhuzichu.mvvm.view.layout.MultiStateView

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-06-18
 * Time: 16:28
 */
@BindingAdapter("viewState")
fun viewState(multiStateView: MultiStateView, @NonNull state: Int? = null) {
    state?.let {
        multiStateView.viewState = it
    }
}