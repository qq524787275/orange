package com.zhuzichu.mvvm.databinding.navigationtabbar

import androidx.annotation.Nullable
import androidx.databinding.BindingAdapter
import com.zhuzichu.mvvm.view.navigationtabbar.ntb.NavigationTabBar

@BindingAdapter(value = ["ntb_bg_color", "ntb_active_color", "ntb_inactive_color"], requireAll = false)
fun bindNavigationTabBar(
    view: NavigationTabBar,
    @Nullable ntb_bg_color: Int?=null,
    @Nullable ntb_active_color: Int?=null,
    @Nullable ntb_inactive_color: Int?=null
) {
    if (ntb_bg_color != null) {
        view.bgColor = ntb_bg_color
    }
    if (ntb_active_color != null) {
        view.activeColor = ntb_active_color
    }
    if (ntb_inactive_color != null) {
        view.inactiveColor = ntb_inactive_color
    }
}
