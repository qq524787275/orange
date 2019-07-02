package com.zhuzichu.orange.shopdetail.adapter

import android.os.Parcelable
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class ShopDetailFragmentPageAdapter(fm: FragmentManager?, private val list: List<Fragment>) : FragmentPagerAdapter(fm) {


    override fun getItem(position: Int): Fragment = list[position]

    override fun getCount(): Int = list.size

    override fun saveState(): Parcelable? {
        return null
    }

}