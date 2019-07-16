package com.zhuzichu.mvvm.view.nicebanner

import android.view.View
import androidx.viewpager.widget.ViewPager

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-07-16
 * Time: 17:07
 */
class NiceContentPageTransformer(private val contentWidthPadding: Float) : ViewPager.PageTransformer {

    override fun transformPage(view: View, position: Float) {
        view.translationX = contentWidthPadding
    }
}