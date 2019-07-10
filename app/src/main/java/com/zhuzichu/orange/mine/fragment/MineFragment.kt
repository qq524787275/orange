package com.zhuzichu.orange.mine.fragment

import android.graphics.drawable.BitmapDrawable
import android.view.animation.AccelerateInterpolator
import android.widget.LinearLayout
import androidx.lifecycle.Observer
import com.zhuzichu.mvvm.base.BaseTopbarFragment
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
        setTitle("我的")

    }

    override fun initViewObservable() {
        _viewModel.uc.onDarkChangeEvent.observe(this, Observer {
            // 获取FloatingActionButton的中心点的坐标
            val v = getSuperTopFragment().view!!
            val content = v.findViewById<LinearLayout>(R.id.layout_content)
            val overlay = v.findViewById<LinearLayout>(R.id.layout_overlay)
            val centerX = (content.left + content.right) / 2
            val centerY = (content.top + content.bottom) / 2
            val finalRadius = hypot(centerX.toDouble(), centerY.toDouble()).toFloat()
            val mCircularReveal = ViewAnimationUtils.createCircularReveal(content, centerX, centerY, 0f, finalRadius)
            mCircularReveal.interpolator = AccelerateInterpolator()
            val drawable = BitmapDrawable(QMUIDrawableHelper.createBitmapFromView(content))

            overlay.setBackgroundDrawable(drawable)
            mCircularReveal.setDuration(500).start()
            _viewModel.color.setDark(it)
            if (it)
                QMUIStatusBarHelper.setStatusBarDarkMode(_viewModel._activity)
            else
                QMUIStatusBarHelper.setStatusBarLightMode(_viewModel._activity)
        })
    }
}