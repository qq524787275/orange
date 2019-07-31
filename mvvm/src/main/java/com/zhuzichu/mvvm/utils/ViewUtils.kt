package com.zhuzichu.mvvm.utils

import androidx.core.view.forEachIndexed
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomnavigation.BottomNavigationView


/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-06-11
 * Time: 09:31
 */
fun closeRecyclerAnimator(recyclerView: RecyclerView) {
    recyclerView.itemAnimator?.addDuration = 0
    recyclerView.itemAnimator?.changeDuration = 0
    recyclerView.itemAnimator?.moveDuration = 0
    recyclerView.itemAnimator?.removeDuration = 0
    (recyclerView.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
    0.0f.toInt()
}

fun getRecyclerPosition(recyclerView: RecyclerView): Int {
    if (recyclerView.childCount > 0) {
        return (recyclerView.getChildAt(0).layoutParams as RecyclerView.LayoutParams).viewAdapterPosition
    }
    return 0
}

fun BottomNavigationView.setupWithViewPager(viewPager: ViewPager) {

    viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
        override fun onPageScrollStateChanged(state: Int) {
        }

        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        }

        override fun onPageSelected(position: Int) {
            this@setupWithViewPager.menu.getItem(position).isChecked = true
        }
    })


    this.setOnNavigationItemSelectedListener {
        this@setupWithViewPager.menu.forEachIndexed { index, item ->
            if (it.itemId == item.itemId) {
                viewPager.setCurrentItem(index, false)
            }
        }
        true
    }
}