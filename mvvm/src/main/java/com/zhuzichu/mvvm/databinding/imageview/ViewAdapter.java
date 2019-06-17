package com.zhuzichu.mvvm.databinding.imageview;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.widget.ImageView;
import androidx.databinding.BindingAdapter;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.zhuzichu.mvvm.global.glide.GlideApp;

public final class ViewAdapter {

    @BindingAdapter(value = {"url", "placeholderRes"}, requireAll = false)
    public static void setImageUri(ImageView imageView, String url, int placeholderRes) {
        if (!TextUtils.isEmpty(url)) {
            //使用Glide框架加载图片
            GlideApp.with(imageView)
                    .load(url)
                    .apply(new RequestOptions().placeholder(placeholderRes))
                    .transition(DrawableTransitionOptions.withCrossFade(250))
                    .into(imageView);
        }
    }


    @BindingAdapter("srcColor")
    public static void srcColor(ImageView imageView, int color) {
        Drawable mutate = imageView.getDrawable().mutate();
        mutate.setColorFilter(color, PorterDuff.Mode.SRC_IN);
        imageView.setImageDrawable(mutate);
    }
}

