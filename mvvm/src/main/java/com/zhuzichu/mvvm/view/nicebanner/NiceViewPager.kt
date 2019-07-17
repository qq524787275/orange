package com.zhuzichu.mvvm.view.nicebanner

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-07-17
 * Time: 11:54
 */
class NiceViewPager : ViewPager {
    private var scrollable = true

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    override fun onTouchEvent(ev: MotionEvent): Boolean {
        return if (this.scrollable) {
            if (currentItem == 0 && childCount == 0) {
                false
            } else super.onTouchEvent(ev)
        } else {
            false
        }
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        return if (this.scrollable) {
            if (currentItem == 0 && childCount == 0) {
                false
            } else super.onInterceptTouchEvent(ev)
        } else {
            false
        }
    }

    fun setScrollable(scrollable: Boolean) {
        this.scrollable = scrollable
    }
}
