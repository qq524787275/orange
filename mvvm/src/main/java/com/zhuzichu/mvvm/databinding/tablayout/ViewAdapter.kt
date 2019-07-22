package com.zhuzichu.mvvm.databinding.tablayout

import androidx.annotation.Nullable
import androidx.databinding.BindingAdapter
import com.google.android.material.tabs.TabLayout
import com.zhuzichu.mvvm.R
import com.zhuzichu.mvvm.utils.toColorById

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-07-22
 * Time: 14:33
 */
@BindingAdapter(value = ["tabIndicatorColor", "tabSelectedTextColor", "tabTextColor"], requireAll = false)
fun bindTabLayout(
    view: TabLayout,
    @Nullable tabIndicatorColor: Int? = null,
    @Nullable tabSelectedTextColor: Int? = null,
    @Nullable tabTextColor: Int? = null
) {
    if (tabIndicatorColor != null) {
        view.setSelectedTabIndicatorColor(tabIndicatorColor)
    }

    var defaultColor = R.color.colorSecondText.toColorById()
    var selectedColor = R.color.colorPrimary.toColorById()

    if (tabSelectedTextColor != null)
        selectedColor = tabSelectedTextColor
    if (tabTextColor != null)
        defaultColor = tabTextColor

    view.setTabTextColors(defaultColor,selectedColor)
}