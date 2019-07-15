package com.zhuzichu.mvvm.databinding.imageview

import android.graphics.PorterDuff
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.zhuzichu.mvvm.global.glide.GlideApp
import com.zhuzichu.mvvm.utils.dip2px
import com.zhuzichu.mvvm.utils.getScreenW
import com.zhuzichu.mvvm.widget.WhiteToAlphaTransformation

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-06-25
 * Time: 17:56
 */

@BindingAdapter(value = ["url", "placeholderRes", "aspecRatio", "margin", "enableWhiteToAlpha"], requireAll = false)
fun setImageUri(
    imageView: ImageView,
    url: Any?,
    placeholderRes: Int,
    aspecRatio: Float,
    margin: Int,
    enableWhiteToAlpha: Boolean
) {
    if (aspecRatio != 0f) {
        imageView.layoutParams.width = getScreenW() - dip2px(margin.toFloat())
        imageView.layoutParams.height = (imageView.layoutParams.width / aspecRatio).toInt()
    }
    if (url != null) {
        //使用Glide框架加载图片
        GlideApp.with(imageView).load(url).apply {
            this.transition(DrawableTransitionOptions.withCrossFade(250))
            if (enableWhiteToAlpha) {
                this.transform(WhiteToAlphaTransformation())
            } else {
                this.apply(RequestOptions().placeholder(placeholderRes))
            }
        }.into(imageView)
    }
}

@BindingAdapter("srcColor")
fun srcColor(imageView: ImageView, color: Int) {
    imageView.setColorFilter(color, PorterDuff.Mode.SRC_IN)
}