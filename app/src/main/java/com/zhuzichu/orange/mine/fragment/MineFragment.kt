package com.zhuzichu.orange.mine.fragment

import android.graphics.drawable.BitmapDrawable
import android.view.View
import android.view.animation.AccelerateInterpolator
import androidx.lifecycle.Observer
import com.zhuzichu.mvvm.base.BaseTopbarFragment
import com.zhuzichu.mvvm.utils.dip2px
import com.zhuzichu.mvvm.utils.helper.QMUIDrawableHelper
import com.zhuzichu.mvvm.utils.helper.QMUIStatusBarHelper
import com.zhuzichu.mvvm.view.reveal.animation.ViewAnimationUtils
import com.zhuzichu.orange.BR
import com.zhuzichu.orange.R
import com.zhuzichu.orange.databinding.FragmentMineBinding
import com.zhuzichu.orange.mine.viewmodel.MineViewModel
import kotlin.math.hypot

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-06-12
 * Time: 16:40
 */
class MineFragment : BaseTopbarFragment<FragmentMineBinding, MineViewModel>() {
    override fun bindVariableId(): Int = BR.viewModel
    override fun setLayoutId(): Int = R.layout.fragment_mine

    override fun initView() {

    }

    override fun initViewObservable() {
        _viewModel.uc.onDarkChangeEvent.observe(this, Observer {
            // 获取FloatingActionButton的中心点的坐标
            val v = getSuperTopFragment().view!!
            val content = v.findViewById<View>(R.id.layout_content)
            val overlay = v.findViewById<View>(R.id.layout_overlay)
            val centerX = (content.left + content.right) - dip2px(60f)
            val centerY = QMUIStatusBarHelper.getStatusbarHeight(activity) + dip2px(25f)
            val pX = centerX - content.left
            val pY = centerY - content.bottom
            val finalRadius = hypot(pX.toDouble(), pY.toDouble()).toFloat()
            val mCircularReveal = ViewAnimationUtils.createCircularReveal(content, centerX, centerY, 0f, finalRadius)
            mCircularReveal.interpolator = AccelerateInterpolator()
            val drawable = BitmapDrawable(resources, QMUIDrawableHelper.createBitmapFromView(content))
            overlay.background = drawable
            mCircularReveal.setDuration(500).start()
            _viewModel.preference.isDark = it.apply {
                _viewModel.color.setDark(this)
                if (this)
                    QMUIStatusBarHelper.setStatusBarDarkMode(_viewModel._activity)
                else
                    QMUIStatusBarHelper.setStatusBarLightMode(_viewModel._activity)
            }
        })
    }
}