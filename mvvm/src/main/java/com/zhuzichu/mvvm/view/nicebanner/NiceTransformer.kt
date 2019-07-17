package com.zhuzichu.mvvm.view.nicebanner

import android.view.View
import androidx.viewpager.widget.ViewPager


abstract class NiceTransformer(private val contentWidthPadding: Float) : ViewPager.PageTransformer {


    override fun transformPage(view: View, position: Float) {
        onPreTransform(view, position)
        when {
            position < -1.0f -> // [-Infinity,-1)
                // This page is way off-screen to the left.
                handleInvisiblePage(view, position)
            position <= 0.0f -> // [-1,0]
                // Use the default slide transition when moving to the left page
                handleLeftPage(view, position)
            position <= 1.0f -> // (0,1]
                handleRightPage(view, position)
            else -> // (1,+Infinity]
                // This page is way off-screen to the right.
                handleInvisiblePage(view, position)
        }
    }


    abstract fun handleInvisiblePage(view: View, position: Float)

    abstract fun handleLeftPage(view: View, position: Float)

    abstract fun handleRightPage(view: View, position: Float)

    open fun onPreTransform(page: View, position: Float) {
        page.translationX = contentWidthPadding
    }
}
