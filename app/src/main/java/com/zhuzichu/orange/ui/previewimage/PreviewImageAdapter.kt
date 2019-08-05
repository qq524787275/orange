package com.zhuzichu.orange.ui.previewimage

import android.app.Activity
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.zhuzichu.mvvm.global.glide.GlideApp
import com.zhuzichu.mvvm.view.imagezoom.ImageViewTouch
import com.zhuzichu.mvvm.view.imagezoom.ImageViewTouchBase
import com.zhuzichu.orange.R

class PreviewImageAdapter(
    var list: List<String>
) : PagerAdapter() {

    override fun isViewFromObject(view: View, `object`: Any): Boolean = view == `object`

    override fun getCount(): Int = list.size

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = LayoutInflater.from(container.context).inflate(R.layout.item_preview_image, null)
        val imageView = view.findViewById<ImageViewTouch>(R.id.image)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            imageView.transitionName = list[position]
        }
        imageView.displayType = ImageViewTouchBase.DisplayType.FIT_TO_SCREEN
        imageView.setSingleTapListener { (container.context as Activity).onBackPressed() }
        GlideApp.with(imageView).load(list[position]).into(imageView)
        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}