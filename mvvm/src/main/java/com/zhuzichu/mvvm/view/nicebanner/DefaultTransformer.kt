package com.zhuzichu.mvvm.view.nicebanner

import android.view.View
import com.zhuzichu.mvvm.utils.logi
import java.lang.Math.abs
import java.lang.Math.max

class DefaultTransformer(contentWidthPadding: Float) : NiceTransformer(contentWidthPadding) {
    private val minScale = 0.92f
    override fun onTransform(page: View, position: Float) {
        val scaleFactor = max(minScale, 1 - abs(position))
        scaleFactor.toString().logi("yijian")
        when {
            position < -1 -> {
                page.scaleX = scaleFactor
                page.scaleY = scaleFactor
            }
            position < 0 -> {
                page.scaleX = scaleFactor
                page.scaleY = scaleFactor
            }
            position in 0.0..1.0 -> {
                page.scaleX = scaleFactor
                page.scaleY = scaleFactor
            }
            position >= 1 -> {
                page.scaleX = scaleFactor
                page.scaleY = scaleFactor
            }
        }
    }
}

