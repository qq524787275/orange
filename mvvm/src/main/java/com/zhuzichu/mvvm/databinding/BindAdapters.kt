package com.zhuzichu.mvvm.databinding

import android.view.View
import androidx.databinding.BindingAdapter
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import com.zhuzichu.mvvm.R
import com.zhuzichu.mvvm.databinding.command.BindingCommand
import com.zhuzichu.mvvm.view.layout.MultiStateView
import kotlinx.android.synthetic.main.error_view.view.*

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