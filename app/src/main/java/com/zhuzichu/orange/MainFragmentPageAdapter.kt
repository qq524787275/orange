package com.zhuzichu.orange

import android.os.Parcelable
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-06-12
 * Time: 16:59
 */
class MainFragmentPageAdapter(fm: FragmentManager?, private val list: List<Fragment>) : FragmentPagerAdapter(fm) {


    override fun getItem(position: Int): Fragment = list[position]

    override fun getCount(): Int = list.size

    override fun saveState(): Parcelable? {
        return null
    }

}