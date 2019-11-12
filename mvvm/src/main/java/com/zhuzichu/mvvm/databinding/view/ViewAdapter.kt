package com.zhuzichu.mvvm.databinding.view

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.view.MotionEvent
import android.view.View
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.view.ViewCompat
import androidx.core.view.ViewPropertyAnimatorListener
import androidx.databinding.BindingAdapter
import com.jakewharton.rxbinding3.view.clicks
import com.jakewharton.rxbinding3.view.longClicks
import com.zhuzichu.mvvm.R
import com.zhuzichu.mvvm.databinding.command.BindingCommand
import com.zhuzichu.mvvm.databinding.command.ResponseCommand
import com.zhuzichu.mvvm.global.AppGlobal
import com.zhuzichu.mvvm.utils.dip2px
import com.zhuzichu.mvvm.utils.getScreenW
import com.zhuzichu.mvvm.utils.helper.QMUIStatusBarHelper
import com.zhuzichu.mvvm.utils.logi
import com.zhuzichu.mvvm.utils.toColorById
import com.zhuzichu.mvvm.widget.CycleInterpolator
import java.util.concurrent.TimeUnit

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-06-28
 * Time: 11:42
 */
//防重复点击间隔(秒)
const val CLICK_INTERVAL = 500

@SuppressLint("CheckResult")
@BindingAdapter(value = ["onClickCommand", "isThrottleFirst", "isScale"], requireAll = false)
    fun onClickCommand(view: View, clickCommand: BindingCommand<*>?, isThrottleFirst: Boolean, isScale: Boolean) {
    if (isThrottleFirst) {
        view.clicks()
            .subscribe {
                if (!isScale)
                    clickCommand?.execute()
                else {
                    ViewCompat.animate(view).setDuration(200).scaleX(0.9f).scaleY(0.9f)
                        .setInterpolator(CycleInterpolator())
                        .setListener(object : ViewPropertyAnimatorListener {
                            override fun onAnimationEnd(view: View?) {
                                clickCommand?.execute()
                            }

                            override fun onAnimationCancel(view: View?) {

                            }

                            override fun onAnimationStart(view: View?) {
                            }
                        })
                        .withLayer()
                        .start()
                }
            }
    } else {
        view.clicks()
            .throttleFirst(CLICK_INTERVAL.toLong(), TimeUnit.MILLISECONDS)//1秒钟内只允许点击1次
            .subscribe {
                if (!isScale)
                    clickCommand?.execute()
                else {
                    ViewCompat.animate(view).setDuration(200).scaleX(0.9f).scaleY(0.9f)
                        .setInterpolator(CycleInterpolator())
                        .setListener(object : ViewPropertyAnimatorListener {
                            override fun onAnimationEnd(view: View?) {
                                clickCommand?.execute()
                            }

                            override fun onAnimationCancel(view: View?) {

                            }

                            override fun onAnimationStart(view: View?) {
                            }
                        })
                        .withLayer()
                        .start()
                }
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

@BindingAdapter(value = ["widthHeightRatio", "widthPadding", "isScreenW"], requireAll = false)
fun setWidthHeightRatio(view: View, ratio: Float, padding: Int, isScreenW: Boolean) {
    val layoutParams = view.layoutParams
    val widh = getScreenW() - dip2px(padding.toFloat())
    layoutParams.height = (widh * ratio).toInt()
    if (isScreenW) {
        layoutParams.width = getScreenW()
    } else {
        layoutParams.width = widh
    }

}

@BindingAdapter(value = ["backgroundNormal", "backgroundPress"], requireAll = false)
fun bindBackgroundColor(view: View, @Nullable backgroundNormal: Int? = null, @Nullable backgroundPress: Int? = null) {
    var backgroundNormalTmp: Int = R.color.colorPrimary.toColorById()
    var backgroundPressTmp: Int = R.color.colorPrimary.toColorById()
    backgroundNormal?.let {
        backgroundNormalTmp = it
    }
    backgroundPress?.let {
        backgroundPressTmp = it
    }

    val colors = intArrayOf(backgroundPressTmp, backgroundPressTmp, backgroundNormalTmp)
    val states = arrayOfNulls<IntArray>(3)

    states[0] = intArrayOf(android.R.attr.state_pressed)
    states[1] = intArrayOf(-android.R.attr.state_enabled)
    states[2] = intArrayOf()

    ViewCompat.setBackgroundTintList(view, ColorStateList(states, colors))
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
        view.setPadding(0, QMUIStatusBarHelper.getStatusbarHeight(AppGlobal.context), 0, 0)
    }
}

