package com.zhuzichu.orange.ui.file.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.zhuzichu.mvvm.base.BaseViewModel
import com.zhuzichu.mvvm.bus.event.SingleLiveEvent
import com.zhuzichu.mvvm.utils.itemBindingOf
import com.zhuzichu.mvvm.utils.itemDiffOf
import com.zhuzichu.orange.BR
import com.zhuzichu.orange.R
import com.zhuzichu.orange.ui.file.filetype.*
import me.tatarka.bindingcollectionadapter2.collections.AsyncDiffObservableList
import java.io.File

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-08-14
 * Time: 10:26
 */
class FileViewModel(application: Application) : BaseViewModel(application) {
    val navList =
        AsyncDiffObservableList(itemDiffOf<ItemNavViewModel> { oldItem, newItem -> oldItem.file.absolutePath == newItem.file.absolutePath })

    val list =
        AsyncDiffObservableList(itemDiffOf<ItemFileViewModel> { oldItem, newItem -> oldItem.file.absolutePath == newItem.file.absolutePath })

    val navItemBind = itemBindingOf<Any>(BR.item, R.layout.item_file_nav)

    val itemBind = itemBindingOf<Any>(BR.item, R.layout.item_file)

    val viewState = MutableLiveData(0)

    val onAddFileNavEvent = SingleLiveEvent<Boolean>()


    val allDefaultFileType: ArrayList<FileType> by lazy {
        val fileTypes = ArrayList<FileType>()
        fileTypes.add(AudioFileType())
        fileTypes.add(RasterImageFileType())
        fileTypes.add(CompressedFileType())
        fileTypes.add(DataBaseFileType())
        fileTypes.add(ExecutableFileType())
        fileTypes.add(FontFileType())
        fileTypes.add(PageLayoutFileType())
        fileTypes.add(TextFileType())
        fileTypes.add(VideoFileType())
        fileTypes.add(WebFileType())
        fileTypes
    }

    fun loadFileList(fileDir: File, isAddNav: Boolean = true) {
        val data = fileDir.listFiles()?.map { ItemFileViewModel(this, it) }
        list.update(data)
        if (isAddNav) {
            navList.update(navList.plus(ItemNavViewModel(this, fileDir)))
            onAddFileNavEvent.call()
        }
        if (data.isNullOrEmpty()) {
            viewState.value = 2
        } else {
            viewState.value = 0
        }
    }
}