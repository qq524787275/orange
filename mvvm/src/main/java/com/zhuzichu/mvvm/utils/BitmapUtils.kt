package com.zhuzichu.mvvm.utils

import android.graphics.Bitmap
import android.graphics.Color


/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-07-12
 * Time: 15:49
 */

// 白色转化为透明色(对像素操作)
fun drawWriteToAlpha(mBitmap: Bitmap): Bitmap {
    val createBitmap = Bitmap.createBitmap(mBitmap.width, mBitmap.height, Bitmap.Config.ARGB_8888)
    val mWidth = mBitmap.width
    val mHeight = mBitmap.height
    for (i in 0 until mHeight) {
        for (j in 0 until mWidth) {
            var color = mBitmap.getPixel(j, i)
            val g = Color.green(color)
            val r = Color.red(color)
            val b = Color.blue(color)
            var a = Color.alpha(color)
            if (g >= 250 && r >= 250 && b >= 250) {
                a = 0
            }
            color = Color.argb(a, r, g, b)
            createBitmap.setPixel(j, i, color)
        }
    }
    return createBitmap
}
