package com.zhuzichu.mvvm.base

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-07-18
 * Time: 11:35
 */
@Suppress("DEPRECATION")
class DefaultFragmentPagerAdapter(
    fm: FragmentManager,
    private val list: List<Fragment>
) : FragmentPagerAdapter(fm,BEHAVIOR_SET_USER_VISIBLE_HINT) {


    override fun getItem(position: Int): Fragment = list[position]

    override fun getCount(): Int = list.size
}