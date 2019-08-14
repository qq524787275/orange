package com.zhuzichu.orange.ui.file.viewmodel

import androidx.lifecycle.MutableLiveData
import com.zhuzichu.mvvm.base.ItemViewModel
import com.zhuzichu.mvvm.databinding.command.BindingCommand
import java.io.File

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-08-14
 * Time: 11:48
 */
class ItemNavViewModel(
    viewModel: FileViewModel,
    val file: File
) : ItemViewModel<FileViewModel>(viewModel) {
    val fileName = MutableLiveData<String>(file.name)

    val onClickItem = BindingCommand<Any>({
        val list = viewModel.navList.dropLastWhile { file.path != (it as ItemNavViewModel).file.path }
        viewModel.navList.update(list)
        viewModel.loadFileList(file,false)
    })
}