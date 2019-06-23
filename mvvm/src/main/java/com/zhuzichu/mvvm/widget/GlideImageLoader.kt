package com.zhuzichu.mvvm.widget

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.zhuzichu.mvvm.R
import com.zhuzichu.mvvm.global.glide.GlideApp
import com.zhuzichu.mvvm.view.banner.loader.ImageLoader

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-05-30
 * Time: 10:52
 */
class GlideImageLoader : ImageLoader() {
    override fun displayImage(context: Context, path: Any?, imageView: ImageView) {
        GlideApp.with(imageView)
            .load(path)
            .apply(RequestOptions().placeholder(R.drawable.no_banner).error(R.drawable.no_banner))
//            .fitCenter()
//            .transition(DrawableTransitionOptions.withCrossFade(200))
            .into(imageView)
    }
}