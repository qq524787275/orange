package com.zhuzichu.mvvm.databinding.viewpager

import androidx.databinding.BindingAdapter
import androidx.viewpager.widget.ViewPager
import com.zhuzichu.mvvm.databinding.command.BindingCommand

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-08-12
 * Time: 15:47
 */

@BindingAdapter(value = ["onPageSelectedCommand"], requireAll = false)
fun bindViewPager(viewPager: ViewPager, onPageSelectedCommand: BindingCommand<Int>?) {
    viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
        override fun onPageScrollStateChanged(state: Int) {
        }

        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        }

        override fun onPageSelected(position: Int) {
            onPageSelectedCommand?.execute(position)
        }

    })
}