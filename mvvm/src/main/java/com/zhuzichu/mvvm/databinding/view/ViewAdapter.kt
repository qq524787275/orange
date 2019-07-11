package com.zhuzichu.mvvm.databinding.view

import android.annotation.SuppressLint
import android.view.MotionEvent
import android.view.View
import androidx.annotation.NonNull
import androidx.databinding.BindingAdapter
import com.jakewharton.rxbinding3.view.clicks
import com.jakewharton.rxbinding3.view.longClicks
import com.zhuzichu.mvvm.databinding.command.BindingCommand
import com.zhuzichu.mvvm.databinding.command.ResponseCommand
import com.zhuzichu.mvvm.global.AppGlobal
import com.zhuzichu.mvvm.utils.dip2px
import com.zhuzichu.mvvm.utils.getScreenW
import com.zhuzichu.mvvm.utils.helper.QMUIStatusBarHelper
import java.util.concurrent.TimeUnit

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-06-28
 * Time: 11:42
 */
//防重复点击间隔(秒)
const val CLICK_INTERVAL = 1

@SuppressLint("CheckResult")
@BindingAdapter(value = ["onClickCommand", "isThrottleFirst"], requireAll = false)
fun onClickCommand(view: View, clickCommand: BindingCommand<*>?, isThrottleFirst: Boolean) {
    if (isThrottleFirst) {
        view.clicks()
            .subscribe {
                clickCommand?.execute()
            }
    } else {
        view.clicks()
            .throttleFirst(CLICK_INTERVAL.toLong(), TimeUnit.SECONDS)//1秒钟内只允许点击1次
            .subscribe {
                clickCommand?.execute()
            }
    }
}

@SuppressLint("CheckResult")
@BindingAdapter(value = ["onLongClickCommand"], requireAll = false)
fun onLongClickCommand(view: View, clickCommand: BindingCommand<*>?) {
    view.longClicks()
        .subscribe {
            clickCommand?.execute()
        }
}

@BindingAdapter(value = ["currentView"], requireAll = false)
fun replyCurrentView(currentView: View, bindingCommand: BindingCommand<*>?) {
    bindingCommand?.execute(currentView)
}

@BindingAdapter("requestFocus")
fun requestFocusCommand(view: View, needRequestFocus: Boolean) {
    if (needRequestFocus) {
        view.isFocusableInTouchMode = true
        view.requestFocus()
    } else {
        view.clearFocus()
    }
}


@BindingAdapter("onFocusChangeCommand")
fun onFocusChangeCommand(view: View, onFocusChangeCommand: BindingCommand<Boolean>?) {
    view.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
        onFocusChangeCommand?.execute(hasFocus)
    }
}

@BindingAdapter(value = ["widthHeightRatio", "widthPadding"], requireAll = false)
fun setWidthHeightRatio(view: View, ratio: Float, padding: Int) {
    val layoutParams = view.layoutParams
    layoutParams.width = getScreenW() - dip2px(padding.toFloat())
    layoutParams.height = (layoutParams.width * ratio).toInt()
}


@BindingAdapter("onTouchCommand")
fun onTouchCommand(view: View, onTouchCommand: ResponseCommand<MotionEvent, Boolean>?) {
    view.setOnTouchListener { _, event ->
        onTouchCommand?.execute(event) ?: false
    }
}

@BindingAdapter("enablePaddingStatusbarHeight")
fun enablePaddingStatusbarHeight(view: View, @NonNull isPadding: Boolean = false) {
    if (isPadding) {
        view.setPadding(0, QMUIStatusBarHelper.getStatusbarHeight(AppGlobal.context), 0, 0);
    }
}



