package com.zhuzichu.mvvm.view.nicebanner

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.viewpager.widget.PagerAdapter
import com.zhuzichu.mvvm.R

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-07-16
 * Time: 16:46
 */
class NiceBannerAdapter(
    private val parent: View,
    private val contentWidthPadding: Float,
    private val onLoadComplete: ((position: Int) -> Unit)? = null
) : PagerAdapter() {

    lateinit var nicePagerAdapter: INicePagerAdapter

    override fun instantiateItem(container: ViewGroup, position: Int): View {
        val inflater = LayoutInflater.from(container.context)
        val wrapper = inflater.inflate(R.layout.item_nice_banner_content, container, false) as ViewGroup

        val view = nicePagerAdapter.instantiateItem(
            inflater,
            wrapper,
            position,
            onLoadComplete
        )
        val layoutParams = ViewGroup.MarginLayoutParams(MATCH_PARENT, WRAP_CONTENT)
        if (position == count - 1) {
            layoutParams.marginEnd = (2 * contentWidthPadding).toInt()
        }
        wrapper.addView(view, layoutParams)
        container.addView(wrapper)
        return wrapper
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun getCount() = nicePagerAdapter.getCount()

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun getPageWidth(position: Int): Float {
        if (position == count - 1) {
            return 1.0f
        }
        return 1.0f - (contentWidthPadding * 2) / parent.width
    }
}