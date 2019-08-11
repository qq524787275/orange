package com.zhuzichu.mvvm.databinding.imageview

import android.graphics.PorterDuff
import android.widget.ImageView
import androidx.annotation.NonNull
import androidx.databinding.BindingAdapter
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.zhuzichu.mvvm.global.glide.GlideApp
import com.zhuzichu.mvvm.utils.dip2px
import com.zhuzichu.mvvm.utils.getScreenW
import com.zhuzichu.mvvm.utils.logi
import com.zhuzichu.mvvm.view.imageview.CircularImageView
import com.zhuzichu.mvvm.view.imagezoom.ImageViewTouch
import com.zhuzichu.mvvm.view.imagezoom.ImageViewTouchBase
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
    @NonNull url: Any? = null,
    placeholderRes: Int,
    aspecRatio: Float,
    margin: Int,
    enableWhiteToAlpha: Boolean
) {
    if (aspecRatio != 0f) {
        imageView.layoutParams.width = getScreenW() - dip2px(margin.toFloat())
        imageView.layoutParams.height = (imageView.layoutParams.width / aspecRatio).toInt()
    }
    if (url != null && !(url is String && url.isBlank())) {

        //使用Glide框架加载图片
        GlideApp.with(imageView).load(url).apply {
            if (imageView !is CircularImageView)
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
fun srcColor(imageView: ImageView, @NonNull color: Int? = null) {
    if (color != null)
        imageView.setColorFilter(color, PorterDuff.Mode.SRC_IN)
}

@BindingAdapter(value = ["civ_shadow_color", "civ_border_color"], requireAll = false)
fun bindCircularImageView(imageView: CircularImageView, @NonNull shadow_color: Int? = null, @NonNull border_color: Int? = null) {
    if (shadow_color != null)
        imageView.shadowColor = shadow_color
    if (border_color != null)
        imageView.borderColor = border_color
}


@BindingAdapter(value = ["displayType"], requireAll = false)
fun bindImageViewTouch(imageView: ImageViewTouch, value: Int) {
    "hahaha:".plus(value).logi()
    when (value) {
        0 -> {
            imageView.displayType = ImageViewTouchBase.DisplayType.NONE
        }
        1 -> {
            imageView.displayType = ImageViewTouchBase.DisplayType.FIT_TO_SCREEN
        }
        2 -> {
            imageView.displayType = ImageViewTouchBase.DisplayType.FIT_IF_BIGGER
        }
        else -> {
        }
    }
}