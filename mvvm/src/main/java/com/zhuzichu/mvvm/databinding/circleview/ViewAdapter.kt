package com.zhuzichu.mvvm.databinding.circleview

import androidx.databinding.BindingAdapter
import com.zhuzichu.mvvm.widget.CircleView

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-07-09
 * Time: 16:44
 */

@BindingAdapter("circleViewColor")
fun circleViewColor(circleView: CircleView, color: Int) {
    circleView.setBackgroundColor(color)
}

