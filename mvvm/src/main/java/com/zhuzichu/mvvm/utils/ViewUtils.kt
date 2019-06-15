package com.zhuzichu.mvvm.utils

import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator


/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-06-11
 * Time: 09:31
 */
fun closeRecyclerAnimator(recyclerView: RecyclerView) {
    recyclerView.itemAnimator?.addDuration = 0
    recyclerView.itemAnimator?.changeDuration = 0
    recyclerView.itemAnimator?.moveDuration = 0
    recyclerView.itemAnimator?.removeDuration = 0
    (recyclerView.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
    0.0f.toInt()
}

fun getRecyclerPosition(recyclerView: RecyclerView): Int {
    if (recyclerView.childCount > 0) {
        return (recyclerView.getChildAt(0).layoutParams as RecyclerView.LayoutParams).viewAdapterPosition
    }
    return 0
}