package com.zhuzichu.mvvm.view.nicebanner

import android.content.Context
import android.view.animation.Interpolator
import android.widget.Scroller

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-07-17
 * Time: 09:53
 */
class BannerScroller : Scroller {
    private var mDuration = 800

    constructor(context: Context) : super(context)

    constructor(context: Context, interpolator: Interpolator) : super(context, interpolator)

    constructor(context: Context, interpolator: Interpolator, flywheel: Boolean) : super(
        context,
        interpolator,
        flywheel
    )

    override fun startScroll(startX: Int, startY: Int, dx: Int, dy: Int, duration: Int) {
        super.startScroll(startX, startY, dx, dy, mDuration)
    }

    override fun startScroll(startX: Int, startY: Int, dx: Int, dy: Int) {
        super.startScroll(startX, startY, dx, dy, mDuration)
    }

    fun setDuration(time: Int) {
        mDuration = time
    }
}
