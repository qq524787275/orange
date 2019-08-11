package com.zhuzichu.orange.widget

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.ViewFlipper
import com.bumptech.glide.request.target.CustomViewTarget
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.zhuzichu.mvvm.utils.logi
import com.zhuzichu.mvvm.view.crop.KropView

class KropTarget(
    private val cropContainer: ViewFlipper,
    private val kropView: KropView,
    private val resetZoom: Boolean
) : SimpleTarget<Bitmap>() {

    init {
        cropLoading()
    }

    override fun onLoadFailed(errorDrawable: Drawable?) {
        cropError()
        "onLoadFailed".logi("zzc")
    }

    override fun onStart() {
        "onStart".logi("zzc")
    }

    override fun onDestroy() {
        super.onDestroy()
        "onDestroy".logi("zzc")
    }


    override fun onStop() {
        super.onStop()
        "onStop".logi("zzc")
    }

    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
        kropView.setBitmap(resource)
        if (resetZoom) {
            kropView.setZoom(1.0f)
        }
        cropLoaded()
        "onResourceReady".logi("zzc")
    }

    private fun cropLoading() {
        cropContainer.displayedChild = 0
    }

    private fun cropLoaded() {
        cropContainer.displayedChild = 1
    }

    private fun cropError() {
        cropContainer.displayedChild = 2
    }
}