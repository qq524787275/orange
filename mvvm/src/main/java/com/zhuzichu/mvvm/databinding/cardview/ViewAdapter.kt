package com.zhuzichu.mvvm.databinding.cardview

import androidx.annotation.NonNull
import androidx.cardview.widget.CardView
import androidx.databinding.BindingAdapter

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-07-10
 * Time: 17:31
 */

@BindingAdapter("cardBackground")
fun textColor(cardView: CardView, @NonNull color: Int? = null) {
    if (color != null) {
        cardView.setCardBackgroundColor(color)
    }
}