package com.zhuzichu.orange.search.viewmodel

import android.app.Application
import androidx.core.os.bundleOf
import androidx.lifecycle.MutableLiveData
import com.zhuzichu.mvvm.base.BaseViewModel
import com.zhuzichu.mvvm.databinding.command.BindingAction
import com.zhuzichu.mvvm.databinding.command.BindingCommand
import com.zhuzichu.mvvm.utils.toast
import com.zhuzichu.orange.search.fragment.SearchResultFragment

class SearchViewModel(application: Application) : BaseViewModel(application) {
    val keyWord = MutableLiveData<String>()

    val clickSearch = BindingCommand<Any>(BindingAction {
        if (keyWord.value!!.isBlank()) {
            "请输入有数据".toast()
            return@BindingAction
        }
        hideSoftKeyBoard()
        startFragment(
            SearchResultFragment(),
            bundleOf(
                SearchResultFragment.KEYWORD to keyWord.value
            )
        )
    })
}