package com.zhuzichu.mvvm.databinding

import android.view.View
import androidx.annotation.NonNull
import androidx.databinding.BindingAdapter
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import com.zhuzichu.mvvm.R
import com.zhuzichu.mvvm.databinding.command.BindingCommand
import com.zhuzichu.mvvm.view.layout.MultiStateView
import com.zhuzichu.mvvm.widget.DotsIndicator

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-05-29
 * Time: 16:48
 */

@BindingAdapter("isGone")
fun bindIsGone(view: View, isGone: Boolean) {
    view.visibility = if (isGone) {
        View.GONE
    } else {
        View.VISIBLE
    }
}


@BindingAdapter(value = ["onRefreshCommand", "onLoadMoreCommand"], requireAll = false)
fun onRefreshAndLoadMoreCommand(
    layout: SmartRefreshLayout,
    onRefreshCommand: BindingCommand<Any>?,
    onLoadMoreCommand: BindingCommand<Any>?
) {
    if (onRefreshCommand == null)
        layout.setEnableRefresh(false)
    if (onLoadMoreCommand == null)
        layout.setEnableLoadMore(false)
    layout.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {
        override fun onLoadMore(refreshLayout: RefreshLayout) {
            onLoadMoreCommand?.execute()
        }

        override fun onRefresh(refreshLayout: RefreshLayout) {
            onRefreshCommand?.execute()
        }
    })
}

@BindingAdapter(value = ["primaryColor", "accentColor"], requireAll = false)
fun refreshColor(
    refresh: SmartRefreshLayout,
    @NonNull primaryColor: Int? = null,
    @NonNull accentColor: Int? = null
) {
    var pcolor = 0
    var acolor = 0
    if (primaryColor != null) {
        pcolor = primaryColor
    }
    if (accentColor != null) {
        acolor = accentColor
    }
    refresh.setPrimaryColors(pcolor, acolor)
}


@BindingAdapter(value = ["onErrorCommand"])
fun onErrorCommand(
    layout: MultiStateView,
    onStateErrorCommand: BindingCommand<Any>?
) {
    layout.getView(MultiStateView.VIEW_STATE_ERROR)
        ?.findViewById<View>(R.id.retry)
        ?.setOnClickListener {
            onStateErrorCommand?.execute()
        }
}

@BindingAdapter(value = ["dot_tint"], requireAll = false)
fun bindDotsIndicator(
    view: DotsIndicator,
    @NonNull color: Int? = null
) {
    color?.let {
        view.setDotTint(color)
    }
}
