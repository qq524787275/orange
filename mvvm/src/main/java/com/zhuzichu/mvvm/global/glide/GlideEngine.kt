package com.zhuzichu.mvvm.global.glide

import android.content.Context
import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.zhihu.matisse.engine.ImageEngine

class GlideEngine : ImageEngine {
    override fun loadThumbnail(
        context: Context?,
        resize: Int,
        placeholder: Drawable?,
        imageView: ImageView?,
        uri: Uri?
    ) {
        Glide.with(context!!).load(uri).placeholder(placeholder).override(resize, resize).centerCrop()
            .into(imageView!!)
    }

    override fun loadGifThumbnail(
        context: Context?,
        resize: Int,
        placeholder: Drawable?,
        imageView: ImageView?,
        uri: Uri?
    ) {
        Glide.with(context!!).load(uri).placeholder(placeholder).override(resize, resize).centerCrop()
            .into(imageView!!)
    }

    override fun loadImage(
        context: Context?,
        resizeX: Int,
        resizeY: Int,
        imageView: ImageView?,
        uri: Uri?
    ) {
        Glide.with(context!!).load(uri).override(resizeX, resizeY)
            .priority(Priority.HIGH).fitCenter().into(imageView!!)
    }

    override fun loadGifImage(
        context: Context?,
        resizeX: Int,
        resizeY: Int,
        imageView: ImageView?,
        uri: Uri?
    ) {
        Glide.with(context!!).load(uri).override(resizeX, resizeY)
            .priority(Priority.HIGH).into(imageView!!)
    }

    override fun supportAnimatedGif(): Boolean {
        return true
    }
}