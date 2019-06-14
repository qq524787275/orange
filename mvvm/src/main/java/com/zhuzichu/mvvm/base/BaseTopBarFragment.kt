package com.zhuzichu.mvvm.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.ViewDataBinding
import com.zhuzichu.mvvm.R
import com.zhuzichu.mvvm.utils.dip2px
import com.zhuzichu.mvvm.utils.helper.QMUIStatusBarHelper
import com.zhuzichu.mvvm.utils.toColor

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-06-14
 * Time: 16:28
 */
abstract class BaseTopBarFragment<V : ViewDataBinding, VM : BaseViewModel> : BaseFragment<V, VM>() {
    private lateinit var mTitle: AppCompatTextView
    private lateinit var mStatuBar: View
    private lateinit var mTopBar: View
    private var statusbarHeight: Int = 0
    private var topBarHeight: Int = 0
    private lateinit var contentLp: FrameLayout.LayoutParams

    open fun setTitle(): String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val contentView = super.onCreateView(inflater, container, savedInstanceState)
        val rootView = inflater.inflate(R.layout.layout_base_topbar, null) as FrameLayout

        mStatuBar = rootView.findViewById(R.id.status_bar)
        mTopBar = rootView.findViewById(R.id.top_bar)

        rootView.addView(contentView)

        statusbarHeight = QMUIStatusBarHelper.getStatusbarHeight(context)
        topBarHeight = dip2px(40f)

        val statusBarLp = mStatuBar.layoutParams as FrameLayout.LayoutParams
        val topBarLp = mTopBar.layoutParams as FrameLayout.LayoutParams
        contentLp = contentView?.layoutParams as FrameLayout.LayoutParams

        statusBarLp.height = statusbarHeight
        topBarLp.height = topBarHeight

        topBarLp.topMargin = statusbarHeight

        mTitle = mTopBar.findViewById(R.id.title) as AppCompatTextView

        if (setTitle() == null) {
            hideTopbar()
        } else {
            mTitle.text = setTitle()
            showTopBar()
        }


        mStatuBar.setBackgroundColor(R.color.white.toColor())
        mTopBar.setBackgroundColor(R.color.white.toColor())
        return rootView
    }

    fun setTitle(title: String) {
        mTitle.text = title
        showTopBar()
    }

    fun showTopBar(){
        mTopBar.visibility = View.VISIBLE
        contentLp.topMargin = topBarHeight.plus(statusbarHeight)
    }
    fun hideTopbar(){
        mTopBar.visibility = View.GONE
        contentLp.topMargin = statusbarHeight
    }
}