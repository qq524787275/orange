package com.zhuzichu.mvvm.view.recyclerview

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.zhuzichu.mvvm.utils.logi


/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-08-26
 * Time: 16:16
 */
class ScrollRecyclerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {

    private var mOnScrollChanged: OnScrollChanged? = null

    override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
        super.onScrollChanged(l, t, oldl, oldt)
        mOnScrollChanged?.onScroll(this, l, t, oldl, oldt)

        "oldl:".plus(oldl).logi("ScrollRecyclerView")
        "oldt:".plus(oldt).logi("ScrollRecyclerView")
    }

    interface OnScrollChanged {
        fun onScroll(
            view: View,
            scrollX: Int,
            scrollY: Int,
            oldScrollX: Int,
            oldScrollY: Int
        )
    }

    fun setOnScrollChanged(onScrollChanged: OnScrollChanged) {
        this.mOnScrollChanged = onScrollChanged
    }
}