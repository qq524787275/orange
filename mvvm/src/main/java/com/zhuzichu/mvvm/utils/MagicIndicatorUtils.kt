package com.zhuzichu.mvvm.utils

import android.content.Context
import androidx.viewpager.widget.ViewPager
import com.zhuzichu.mvvm.global.color.ColorGlobal
import com.zhuzichu.mvvm.view.magicindicator.MagicIndicator
import com.zhuzichu.mvvm.view.magicindicator.ViewPagerHelper
import com.zhuzichu.mvvm.view.magicindicator.buildins.commonnavigator.CommonNavigator
import com.zhuzichu.mvvm.view.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import com.zhuzichu.mvvm.view.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import com.zhuzichu.mvvm.view.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import com.zhuzichu.mvvm.view.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator
import com.zhuzichu.mvvm.view.magicindicator.ext.titles.ScaleTransitionPagerTitleView

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-05-28
 * Time: 14:22
 */
fun initMagicIndicator(
    context: Context?, titles: List<String>,
    viewPager: ViewPager? = null,
    magicIndicator: MagicIndicator,
    onClickItemListener: ((position: Int) -> Unit?)? = null
) {
    val commonNavigator = CommonNavigator(context)
    commonNavigator.isSkimOver = true
    commonNavigator.isAdjustMode = true
    commonNavigator.adapter = object : CommonNavigatorAdapter() {
        override fun getCount(): Int {
            return titles.size
        }

        override fun getTitleView(context: Context?, index: Int): IPagerTitleView {
            val simplePagerTitleView = ScaleTransitionPagerTitleView(context)
            val colorPrimary = ColorGlobal.colorPrimary.value!!
            val textColorSecond = ColorGlobal.textColorSecond.get()!!
            simplePagerTitleView.text = titles[index]
            simplePagerTitleView.textSize = 18f
            simplePagerTitleView.setPadding(0, 0, 0, 0)
            simplePagerTitleView.normalColor = textColorSecond
            simplePagerTitleView.selectedColor = colorPrimary
            simplePagerTitleView.setOnClickListener {
                onClickItemListener?.invoke(index)
                viewPager?.currentItem = index
            }
            return simplePagerTitleView
        }

        override fun getIndicator(context: Context?): IPagerIndicator {
            val colorPrimary = ColorGlobal.colorPrimary.value!!
            val indicator = LinePagerIndicator(context)
            indicator.mode = LinePagerIndicator.MODE_EXACTLY
            indicator.lineHeight = dip2px(3f).toFloat()
            indicator.lineWidth = dip2px(30f).toFloat()
            indicator.roundRadius = dip2px(3f).toFloat()
            indicator.setColors(colorPrimary)
            return indicator
        }
    }
    magicIndicator.navigator = commonNavigator
    ViewPagerHelper.bind(magicIndicator, viewPager)
}