package com.zhuzichu.mvvm.databinding.ribbon

import androidx.annotation.NonNull
import androidx.databinding.BindingAdapter
import com.zhuzichu.mvvm.view.ribbon.ShimmerRibbonView

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-07-24
 * Time: 17:44
 */

@BindingAdapter(value = ["shimmer_ribbon_text"], requireAll = false)
fun bindShimmerRibbonView(
    view: ShimmerRibbonView,
    @NonNull shimmer_ribbon_text: String? = null
) {
    shimmer_ribbon_text?.let {
        view.ribbon.text = it
    }
}
