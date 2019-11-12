package com.zhuzichu.mvvm.base

import android.annotation.SuppressLint
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
@SuppressLint("WrongConstant")
@Suppress("DEPRECATION")
class DefaultFragmentPagerAdapter(
    fm: FragmentManager,
    private val list: List<Fragment>,
    private val titles: List<String>? = null
) : FragmentPagerAdapter(fm, BEHAVIOR_SET_USER_VISIBLE_HINT) {


    override fun getItem(position: Int): Fragment = list[position]

    override fun getCount(): Int = list.size

    override fun getPageTitle(position: Int): CharSequence? {
        if (titles == null)
            return super.getPageTitle(position)
        else
            return titles[position]
    }
}