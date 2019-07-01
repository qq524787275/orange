package com.zhuzichu.orange.ui.nineimage

import android.view.View
import android.widget.ImageView
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.zhuzichu.orange.ui.previewimage.PreviewImageActivity
import com.zhuzichu.mvvm.databinding.command.BindingCommand

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-06-26
 * Time: 18:31
 */
class ItemNineImageViewModel(
    var recyclerView: RecyclerView,
    var list: ArrayList<String>,
    var url: String
) : ViewModel() {
    var imageView: ImageView? = null

    val onClickItem = BindingCommand<Any>({
        PreviewImageActivity.start(recyclerView.context, list, url)
    })

    val onImageView = BindingCommand<ImageView>(consumer = {
        imageView = it
    })
}