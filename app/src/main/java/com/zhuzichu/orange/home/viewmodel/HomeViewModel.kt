package com.zhuzichu.orange.home.viewmodel

import android.app.Application
import com.zhuzichu.mvvm.base.BaseViewModel
import com.zhuzichu.mvvm.databinding.command.BindingAction
import com.zhuzichu.mvvm.databinding.command.BindingCommand
import com.zhuzichu.orange.search.fragment.SearchFragment
import me.yokeyword.fragmentation.ISupportFragment

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-06-13
 * Time: 13:27
 */
class HomeViewModel(application: Application) : BaseViewModel(application) {

    val clickSearchLayout = BindingCommand<Any>(BindingAction {
        startFragment(SearchFragment(), launchMode = ISupportFragment.SINGLETASK)
    })


}