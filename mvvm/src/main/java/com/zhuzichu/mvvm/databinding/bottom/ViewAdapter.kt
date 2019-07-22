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
    @Nullable textColor: Int?,
    @Nullable selectedTextColor: Int?,
    @Nullable iconColor: Int?,
    @Nullable selectedIconColor: Int?
) {
    var textColorOne: Int = R.color.colorSecondText.toColorById()
    var selectedTextColorOne = R.color.colorPrimary.toColorById()
    var iconColorOne = R.color.colorSecondText.toColorById()
    var selectedIconColorOne = R.color.colorPrimary.toColorById()
    textColor?.let {
        textColorOne = it
    }
    selectedTextColor?.let {
        selectedTextColorOne = it
    }
    iconColor?.let {
        iconColorOne = it
    }
    selectedIconColor?.let {
        selectedIconColorOne = it
    }
    val textColors = intArrayOf(selectedTextColorOne, selectedTextColorOne, textColorOne)
    val textStates = arrayOfNulls<IntArray>(3)
    textStates[0] = intArrayOf(android.R.attr.state_checked)
    textStates[1] = intArrayOf(android.R.attr.state_pressed)
    textStates[2] = intArrayOf()
    view.itemTextColor = ColorStateList(textStates, textColors)

    val iconColors = intArrayOf(selectedIconColorOne, selectedIconColorOne, iconColorOne)
    val iconStates = arrayOfNulls<IntArray>(3)
    iconStates[0] = intArrayOf(android.R.attr.state_checked)
    iconStates[1] = intArrayOf(android.R.attr.state_pressed)
    iconStates[2] = intArrayOf()
    view.itemIconTintList = ColorStateList(iconStates, iconColors)

}