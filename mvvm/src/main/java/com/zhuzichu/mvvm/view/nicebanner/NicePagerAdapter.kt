package com.zhuzichu.mvvm.view.nicebanner

import android.content.Context
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.zhuzichu.mvvm.R
import com.zhuzichu.mvvm.global.glide.GlideApp

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-07-16
 * Time: 17:25
 */
class NicePagerAdapter(
    val context: Context,
    val list: List<String>
) : INicePagerAdapter {

    val listBitmap = arrayOfNulls<Bitmap>(list.size)


    override fun instantiateItem(
        inflater: LayoutInflater,
        container: ViewGroup,
        position: Int,
        niceBannerAdapter: NiceBannerAdapter
    ): View {
        val content = inflater.inflate(R.layout.item_nice_banner, container, false)
        val imageView = content.findViewById<ImageView>(R.id.item_banner_image)
        GlideApp.with(imageView).asBitmap().load(list[position]).into(object : SimpleTarget<Bitmap>() {
            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                imageView.setImageBitmap(resource)
                listBitmap[position] = resource
            }
        })

        return content
    }

    override fun getCount(): Int = list.size

    override fun isUpdatingBackgroundColor(): Boolean = true

    override fun requestBitmapAtPosition(position: Int): Bitmap? {
        // Return the bitmap used for the position
        return listBitmap[position]
    }
}