package com.zhuzichu.orange.ui.file.viewmodel

import androidx.lifecycle.MutableLiveData
import com.zhuzichu.mvvm.base.ItemViewModel
import com.zhuzichu.mvvm.databinding.command.BindingCommand
import com.zhuzichu.mvvm.utils.toast
import com.zhuzichu.orange.R
import java.io.File

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-08-14
 * Time: 11:48
 */
class ItemFileViewModel(
    viewModel: FileViewModel,
    val file: File
) : ItemViewModel<FileViewModel>(viewModel) {

    val fileName = MutableLiveData<String>(file.name)

    val fileIcon = MutableLiveData<Int>().apply {
        if (file.isDirectory) {
            value = R.drawable.ic_folder_file_picker
            return@apply
        }

        viewModel.allDefaultFileType.forEach {
            value = if (it.verify(file.name)) {
                it.fileIconResId
            } else {
                R.drawable.ic_unknown_file_picker
            }
        }
    }

    val onClickItem = BindingCommand<Any>({
        if (file.isDirectory) {
            viewModel.loadFileList(file)
        } else {
            "当前不支持文件预览".toast()
        }
    })
}