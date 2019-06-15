package com.zhuzichu.mvvm.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.appcompat.widget.AppCompatImageView
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
    private lateinit var mRightLayout: LinearLayout;

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
        topBarHeight = dip2px(50f)

        val statusBarLp = mStatuBar.layoutParams as FrameLayout.LayoutParams
        val topBarLp = mTopBar.layoutParams as FrameLayout.LayoutParams
        contentLp = contentView?.layoutParams as FrameLayout.LayoutParams

        statusBarLp.height = statusbarHeight
        topBarLp.height = topBarHeight

        topBarLp.topMargin = statusbarHeight

        mTitle = mTopBar.findViewById(R.id.title) as AppCompatTextView
        mRightLayout = mTopBar.findViewById(R.id.layout_right) as LinearLayout

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

    fun showTopBar() {
        mTopBar.visibility = View.VISIBLE
        contentLp.topMargin = topBarHeight.plus(statusbarHeight)
    }

    fun hideTopbar() {
        mTopBar.visibility = View.GONE
        contentLp.topMargin = statusbarHeight
    }

    fun addRightIcon(iconId: Int, onClickListener: View.OnClickListener? = null) {
        val imageLayout = layoutInflater.inflate(R.layout.item_topbar_right_btn, null) as RelativeLayout
        mRightLayout.addView(imageLayout)
        imageLayout.layoutParams.height = topBarHeight
        imageLayout.layoutParams.width = topBarHeight
        val icon = imageLayout.findViewById(R.id.image) as AppCompatImageView
        icon.setImageResource(iconId)
        if (onClickListener != null)
            imageLayout.setOnClickListener(onClickListener)
    }
}