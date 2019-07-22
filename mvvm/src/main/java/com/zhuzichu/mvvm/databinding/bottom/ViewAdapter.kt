package com.zhuzichu.mvvm.databinding.bottom

import android.content.res.ColorStateList
import androidx.annotation.Nullable
import androidx.databinding.BindingAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.zhuzichu.mvvm.R
import com.zhuzichu.mvvm.utils.toColorById

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-07-22
 * Time: 17:22
 */

@BindingAdapter(value = ["textColor", "selectedTextColor", "iconColor", "selectedIconColor"], requireAll = false)
fun onBindBottomNavigationView(
    view: BottomNavigationView,
    @Nullable textColor: Int = R.color.colorSecondText.toColorById(),
    @Nullable selectedTextColor: Int = R.color.colorPrimary.toColorById(),
    @Nullable iconColor: Int = R.color.colorSecondText.toColorById(),
    @Nullable selectedIconColor: Int = R.color.colorPrimary.toColorById()
) {
    val textColors = intArrayOf(selectedTextColor, selectedTextColor, textColor)
    val textStates = arrayOfNulls<IntArray>(3)
    textStates[0] = intArrayOf(android.R.attr.state_checked)
    textStates[1] = intArrayOf(android.R.attr.state_pressed)
    textStates[2] = intArrayOf()
    view.itemTextColor = ColorStateList(textStates, textColors)

    val iconColors = intArrayOf(selectedIconColor, selectedTextColor, iconColor)
    val iconStates = arrayOfNulls<IntArray>(3)
    iconStates[0] = intArrayOf(android.R.attr.state_checked)
    iconStates[1] = intArrayOf(android.R.attr.state_pressed)
    iconStates[2] = intArrayOf()
    view.itemIconTintList = ColorStateList(iconStates, iconColors)

}