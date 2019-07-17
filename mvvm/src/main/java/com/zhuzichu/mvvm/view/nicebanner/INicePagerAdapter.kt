package com.zhuzichu.mvvm.view.nicebanner

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-07-16
 * Time: 16:48
 */
interface INicePagerAdapter {

    fun instantiateItem(
        inflater: LayoutInflater,
        container: ViewGroup,
        position: Int,
        onLoadComplete: ((position: Int) -> Unit)?=null
    ): View

    fun getCount(): Int

    fun isUpdatingBackgroundColor(): Boolean {
        return false
    }

    fun requestBitmapAtPosition(position: Int): Bitmap? {
        return null
    }

}