package com.zhuzichu.mvvm.view.tourguide.utils

import android.graphics.Point
import android.view.View

val View.locationOnScreen: Point
    get() = IntArray(2).let {
        getLocationOnScreen(it)
        Point(it[0], it[1])
    }