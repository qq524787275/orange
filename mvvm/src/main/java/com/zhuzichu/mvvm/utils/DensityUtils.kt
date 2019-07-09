package com.zhuzichu.mvvm.utils

import android.app.Activity
import android.util.DisplayMetrics
import android.util.TypedValue
import com.zhuzichu.mvvm.global.AppGlobal

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-05-28
 * Time: 14:50
 */
/**
 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
 */
fun dip2px(dpValue: Float): Int {
    val scale = AppGlobal.context.resources.displayMetrics.density
    return (dpValue * scale + 0.5f).toInt()
}

/**
 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
 */
fun px2dip(pxValue: Float): Int {
    val scale = AppGlobal.context.resources.displayMetrics.density
    return (pxValue / scale + 0.5f).toInt()
}

/**
 * 根据手机的分辨率从 px(像素) 的单位 转成为 sp
 */
fun px2sp(pxValue: Float): Int {
    val fontScale = AppGlobal.context.resources.displayMetrics.scaledDensity
    return (pxValue / fontScale + 0.5f).toInt()
}

/**
 * 根据手机的分辨率从 sp 的单位 转成为 px
 */
fun sp2px(spValue: Float): Int {
    val fontScale = AppGlobal.context.resources.displayMetrics.scaledDensity
    return (spValue * fontScale + 0.5f).toInt()
}

/**
 * 获取dialog宽度(屏幕宽-100px)
 */
fun getDialogW(aty: Activity): Int {
    var dm = DisplayMetrics()
    dm = aty.resources.displayMetrics
// int w = aty.getWindowManager().getDefaultDisplay().getWidth() - 100;
    return dm.widthPixels - 100
}

/**
 * 获取屏幕宽度
 */
fun getScreenW(): Int {
    val dm = getScreenDM()
// int w = aty.getWindowManager().getDefaultDisplay().getWidth();
    return dm.widthPixels
}

/**
 * 获取屏幕高度
 */
fun getScreenH(): Int {
    val dm = getScreenDM()

// int h = aty.getWindowManager().getDefaultDisplay().getHeight();
    return dm.heightPixels
}

/**
 * 获取屏幕高宽
 *
 * @param a
 * @return
 */
fun getScreenWH(): String {
    val metrics = getScreenDM()
    val w = metrics.widthPixels
    val h = metrics.heightPixels
    return w.toString() + "x" + h.toString()
}


/**
 * 获取DisplayMetrics
 *
 * @param aty
 * @return
 */
fun getScreenDM(): DisplayMetrics {
    return AppGlobal.context.resources.displayMetrics
}


/**
 * 获取状态栏高
 *
 * @return
 */
fun getStatuBarH(): Int {
    val resources = AppGlobal.context.resources
    val resourcesId = resources.getIdentifier("status_bar_height", "dimen", "android")
    return resources.getDimensionPixelSize(resourcesId)
}

fun dip2sp(sp: Float): Int {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, AppGlobal.context.resources.displayMetrics).toInt()
}
