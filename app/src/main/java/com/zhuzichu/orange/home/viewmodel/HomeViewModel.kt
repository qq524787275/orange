package com.zhuzichu.orange.home.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import androidx.databinding.ObservableArrayList
import com.zhuzichu.mvvm.base.BaseViewModel
import com.zhuzichu.mvvm.bus.event.SingleLiveEvent
import com.zhuzichu.mvvm.databinding.command.BindingAction
import com.zhuzichu.mvvm.databinding.command.BindingCommand
import com.zhuzichu.mvvm.utils.bindToLifecycle
import com.zhuzichu.mvvm.utils.exceptionTransformer
import com.zhuzichu.mvvm.utils.itemBindingOf
import com.zhuzichu.mvvm.utils.schedulersTransformer
import com.zhuzichu.orange.BR
import com.zhuzichu.orange.R
import com.zhuzichu.orange.bean.BrandBean
import com.zhuzichu.orange.bean.TalentcatBean
import com.zhuzichu.orange.repository.NetRepositoryImpl
import com.zhuzichu.orange.search.fragment.SearchFragment
import me.tatarka.bindingcollectionadapter2.collections.DiffObservableList
import me.yokeyword.fragmentation.ISupportFragment

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-06-13
 * Time: 13:27
 */
@SuppressLint("CheckResult")
class HomeViewModel(application: Application) : BaseViewModel(application) {

    val itemNavigation = itemBindingOf<ItemNavigationViewModel>(BR.item, R.layout.item_home_navigation)
    val listNavigation = ObservableArrayList<ItemNavigationViewModel>().apply {
        addAll(listOf(
            ItemNavigationViewModel(this@HomeViewModel,"淘宝",R.mipmap.cheng_search_t_xuxia),
            ItemNavigationViewModel(this@HomeViewModel,"京东",R.mipmap.cheng_search_j_xuxia),
            ItemNavigationViewModel(this@HomeViewModel,"拼多多",R.mipmap.cheng_search_p_xuxia)
        ))
    }

    val uc = UIChangeObservable()

    inner class UIChangeObservable {
        val onBannerLoadSuccess = SingleLiveEvent<List<TalentcatBean.Topdata>>()
    }

    val clickSearchLayout = BindingCommand<Any>(BindingAction {
        startFragment(SearchFragment(), launchMode = ISupportFragment.SINGLETASK)
    })


    fun loadBanner() {
        NetRepositoryImpl.getTalentcat()
            .compose(bindToLifecycle(getLifecycleProvider()))
            .compose(schedulersTransformer())
            .compose(exceptionTransformer())
            .map {
                it.data.topdata
            }
            .subscribe({
                uc.onBannerLoadSuccess.value = it
            }, {
                handleThrowable(it)
            })
    }
}