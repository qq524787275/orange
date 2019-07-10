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
import androidx.lifecycle.Observer
import com.zhuzichu.mvvm.R
import com.zhuzichu.mvvm.global.color.ColorGlobal
import com.zhuzichu.mvvm.utils.dip2px
import com.zhuzichu.mvvm.utils.helper.QMUIStatusBarHelper
import com.zhuzichu.mvvm.utils.toColorById

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-06-14
 * Time: 16:28
 */
abstract class BaseTopbarFragment<V : ViewDataBinding, VM : BaseViewModel> : BaseFragment<V, VM>() {
    private val _title by lazy {
        _topBar.findViewById(R.id.title) as AppCompatTextView
    }
    private lateinit var _statuBar: View
    private lateinit var _topBar: View

    private val _rightLayout by lazy {
        _topBar.findViewById(R.id.layout_right) as LinearLayout
    }
    private val _leftLayout by lazy {
        _topBar.findViewById(R.id.layout_left) as LinearLayout
    }

    private var _statusbarHeight: Int = 0
    private var _topBarHeight: Int = 0
    private lateinit var _contentLp: FrameLayout.LayoutParams

    open fun setTitle(): String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val contentView = super.onCreateView(inflater, container, savedInstanceState)
        val rootView = inflater.inflate(R.layout.layout_base_topbar, null) as FrameLayout

        _statuBar = rootView.findViewById(R.id.status_bar)
        _topBar = rootView.findViewById(R.id.top_bar)

        rootView.addView(contentView)

        _statusbarHeight = QMUIStatusBarHelper.getStatusbarHeight(context)
        _topBarHeight = dip2px(50f)

        val statusBarLp = _statuBar.layoutParams as FrameLayout.LayoutParams
        val topBarLp = _topBar.layoutParams as FrameLayout.LayoutParams
        _contentLp = contentView?.layoutParams as FrameLayout.LayoutParams

        statusBarLp.height = _statusbarHeight
        topBarLp.height = _topBarHeight

        topBarLp.topMargin = _statusbarHeight


        if (setTitle() == null) {
            hideTopbar()
        } else {
            _title.text = setTitle()
            showTopBar()
        }

        initObservable()
        _statuBar.setBackgroundColor(R.color.white.toColorById())
        _topBar.setBackgroundColor(R.color.white.toColorById())
        return rootView
    }

    private fun initObservable() {
        ColorGlobal.isDark.observe(this, Observer {
            _topBar.setBackgroundColor(ColorGlobal.contentBackground.get()!!)
            _title.setTextColor(ColorGlobal.textColorPrimary.get()!!)
            _statuBar.setBackgroundColor(ColorGlobal.contentBackground.get()!!)
        })
    }

    fun setTitle(title: String) {
        _title.text = title
        showTopBar()
    }

    fun showTopBar() {
        _topBar.visibility = View.VISIBLE
        _contentLp.topMargin = _topBarHeight.plus(_statusbarHeight)
    }

    fun hideTopbar() {
        _topBar.visibility = View.GONE
        _contentLp.topMargin = _statusbarHeight
    }

    fun setStatusBarColor(color: Int) {
        _statuBar.setBackgroundColor(color)
    }

    fun addRightIcon(iconId: Int, onClickListener: View.OnClickListener? = null) {
        val imageLayout = layoutInflater.inflate(R.layout.item_topbar_right_left_btn, null) as RelativeLayout
        _rightLayout.addView(imageLayout)
        _rightLayout.visibility = View.VISIBLE
        imageLayout.layoutParams.height = _topBarHeight
        imageLayout.layoutParams.width = _topBarHeight
        val icon = imageLayout.findViewById(R.id.image) as AppCompatImageView
        icon.setImageResource(iconId)
        if (onClickListener != null)
            imageLayout.setOnClickListener(onClickListener)
    }


    fun addLeftIcon(iconId: Int, onClickListener: View.OnClickListener? = null) {
        val imageLayout = layoutInflater.inflate(R.layout.item_topbar_right_left_btn, null) as RelativeLayout
        _leftLayout.addView(imageLayout)
        _leftLayout.visibility = View.VISIBLE
        (_title.layoutParams as RelativeLayout.LayoutParams).marginStart = 0
        imageLayout.layoutParams.height = _topBarHeight
        imageLayout.layoutParams.width = _topBarHeight
        val icon = imageLayout.findViewById(R.id.image) as AppCompatImageView
        icon.setImageResource(iconId)
        if (onClickListener != null)
            imageLayout.setOnClickListener(onClickListener)
    }
}