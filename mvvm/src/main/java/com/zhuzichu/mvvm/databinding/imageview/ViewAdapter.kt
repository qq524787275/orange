package com.zhuzichu.mvvm.databinding.imageview

import android.graphics.PorterDuff
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.zhuzichu.mvvm.global.glide.GlideApp
import com.zhuzichu.mvvm.utils.dip2px
import com.zhuzichu.mvvm.utils.getScreenW

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-06-25
 * Time: 17:56
 */

@BindingAdapter(value = ["url", "placeholderRes", "aspecRatio", "margin"], requireAll = false)
fun setImageUri(imageView: ImageView, url: Any?, placeholderRes: Int, aspecRatio: Float, margin: Int) {
    if (aspecRatio != 0f) {
        imageView.layoutParams.width = getScreenW() - dip2px(margin.toFloat())
        imageView.layoutParams.height = (imageView.layoutParams.width / aspecRatio).toInt()
    }

    if (url != null) {
        //使用Glide框架加载图片
        GlideApp.with(imageView)
            .load(url)
            .apply(RequestOptions().placeholder(placeholderRes))
            .transition(DrawableTransitionOptions.withCrossFade(250))
            .centerCrop()
            .into(imageView)
    }
}

@BindingAdapter("srcColor")
fun srcColor(imageView: ImageView, color: Int) {
    val mutate = imageView.drawable.mutate()
    mutate.setColorFilter(color, PorterDuff.Mode.SRC_IN)
    imageView.setImageDrawable(mutate)
}