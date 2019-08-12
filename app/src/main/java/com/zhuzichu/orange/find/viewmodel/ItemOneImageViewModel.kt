package com.zhuzichu.orange.find.viewmodel

import androidx.core.os.bundleOf
import com.zhuzichu.mvvm.base.BaseViewModel
import com.zhuzichu.mvvm.base.ItemViewModel
import com.zhuzichu.mvvm.databinding.command.BindingCommand
import com.zhuzichu.orange.ui.picture.PictureActivity

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-07-29
 * Time: 16:26
 */
class ItemOneImageViewModel(
    viewModel: BaseViewModel,
    var url: String,
    var position: Int,
    var list: List<String>
) : ItemViewModel<BaseViewModel>(viewModel) {

    val onClickItem = BindingCommand<Any>({
        //        PreviewImageActivity.start(viewModel._context, list as ArrayList<String>, position)
        viewModel.startActivity(
            clz = PictureActivity::class.java, bundle = bundleOf(
                PictureActivity.URLS to list,
                PictureActivity.POSITION to position
            )
        )
    })
}