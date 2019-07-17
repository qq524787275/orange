package com.zhuzichu.mvvm.view.nicebanner

import android.view.View
import androidx.viewpager.widget.ViewPager

abstract class NiceTransformer(private val contentWidthPadding: Float) : ViewPager.PageTransformer {

    protected abstract fun onTransform(page: View, position: Float)

    override fun transformPage(page: View, position: Float) {
        onPreTransform(page, position)
        onTransform(page, position)
        onPostTransform(page, position)
    }


    open fun onPreTransform(page: View, position: Float) {
        page.translationX = contentWidthPadding
    }


    open fun onPostTransform(page: View, position: Float) {}

    companion object {

        protected fun min(`val`: Float, min: Float): Float {
            return if (`val` < min) min else `val`
        }
    }

}
