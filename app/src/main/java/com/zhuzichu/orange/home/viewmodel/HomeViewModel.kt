package com.zhuzichu.orange.home.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import androidx.databinding.ObservableArrayList
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.zhuzichu.mvvm.base.BaseRes
import com.zhuzichu.mvvm.base.BaseViewModel
import com.zhuzichu.mvvm.bus.event.SingleLiveEvent
import com.zhuzichu.mvvm.databinding.command.BindingCommand
import com.zhuzichu.mvvm.utils.*
import com.zhuzichu.orange.BR
import com.zhuzichu.orange.bean.DeserveBean
import com.zhuzichu.orange.bean.TalentcatBean
import com.zhuzichu.orange.repository.NetRepositoryImpl
import com.zhuzichu.orange.search.fragment.SearchFragment
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import me.tatarka.bindingcollectionadapter2.collections.AsyncDiffObservableList
import me.tatarka.bindingcollectionadapter2.collections.MergeObservableList
import me.tatarka.bindingcollectionadapter2.itembindings.OnItemBindClass
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

    val uc = UIChangeObservable()

    inner class UIChangeObservable {
        val onBannerLoadSuccess = SingleLiveEvent<List<TalentcatBean.Topdata>>()
        val finishRefreshing = SingleLiveEvent<Any>()
        val finishLoadmore = SingleLiveEvent<Any>()
        val finishLoadMoreWithNoMoreData = SingleLiveEvent<Any>()
    }

    val itemBindNavigation =
        itemBindingOf<ItemNavigationViewModel>(BR.item, com.zhuzichu.orange.R.layout.item_home_navigation)
    val listNavigation = ObservableArrayList<ItemNavigationViewModel>().apply {
        addAll(
            listOf(
                ItemNavigationViewModel(this@HomeViewModel, "榜单", com.zhuzichu.orange.R.mipmap.a1),
                ItemNavigationViewModel(this@HomeViewModel, "品牌", com.zhuzichu.orange.R.mipmap.a2),
                ItemNavigationViewModel(this@HomeViewModel, "抢购", com.zhuzichu.orange.R.mipmap.a3),
                ItemNavigationViewModel(this@HomeViewModel, "抖商", com.zhuzichu.orange.R.mipmap.a4),
                ItemNavigationViewModel(this@HomeViewModel, "达人说", com.zhuzichu.orange.R.mipmap.a5),
                ItemNavigationViewModel(this@HomeViewModel, "flutter", com.zhuzichu.orange.R.mipmap.a6),
                ItemNavigationViewModel(this@HomeViewModel, "xxx", com.zhuzichu.orange.R.mipmap.a7),
                ItemNavigationViewModel(this@HomeViewModel, "xxx", com.zhuzichu.orange.R.mipmap.a8),
                ItemNavigationViewModel(this@HomeViewModel, "xxx", com.zhuzichu.orange.R.mipmap.a9),
                ItemNavigationViewModel(this@HomeViewModel, "xxx", com.zhuzichu.orange.R.mipmap.a10)
            )
        )
    }

    val itemBind = OnItemBindClass<Any>().apply {
        map<ItemHomeClassViewModel>(BR.item, com.zhuzichu.orange.R.layout.item_home_class)
        map<ItemHomeDeserveViewModel>(BR.item, com.zhuzichu.orange.R.layout.item_home_deserve)
    }.toItemBinding()

    val deserveList = AsyncDiffObservableList(object : DiffUtil.ItemCallback<Any>() {
        override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean {
            return if (oldItem is ItemHomeDeserveViewModel && newItem is ItemHomeDeserveViewModel) {
                oldItem.deserveBean.itemid == newItem.deserveBean.itemid
            } else oldItem == newItem
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean = oldItem == newItem
    })


    val list = MergeObservableList<Any>()
        .insertItem(ItemHomeClassViewModel(this@HomeViewModel, "今日值得买"))
        .insertList(deserveList)

    val spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
        override fun getSpanSize(position: Int): Int {
            val item = list[position]
            if (item is ItemHomeClassViewModel)
                return 2
            return 1
        }
    }


    val onClickSearch = BindingCommand<Any>({
        startFragment(SearchFragment(), launchMode = ISupportFragment.SINGLETASK)
    })

    fun loadHomeData() {
        Flowable.zip(
            NetRepositoryImpl.getTalentcat().subscribeOn(Schedulers.io()).compose(exceptionTransformer()).compose(
                bindToLifecycle(getLifecycleProvider())
            ),
            NetRepositoryImpl.getDeserveList().subscribeOn(Schedulers.io()).compose(exceptionTransformer()).compose(
                bindToLifecycle(getLifecycleProvider())
            ),
            BiFunction<BaseRes<TalentcatBean>, BaseRes<List<DeserveBean>>, HomeData> { t1, t2 ->
                HomeData(t1, t2)
            }
        ).observeOn(AndroidSchedulers.mainThread())
            .compose(bindToLifecycle(getLifecycleProvider()))
            .doFinally {
                uc.finishRefreshing.call()
            }
            .subscribe({
                uc.onBannerLoadSuccess.value = it.talentcat.data.topdata
                deserveList.update(it.deserveList.item_info.map { item ->
                    ItemHomeDeserveViewModel(this@HomeViewModel, item)
                })

            }, {
                handleThrowable(it)
            })
    }

    data class HomeData(
        var talentcat: BaseRes<TalentcatBean>,
        var deserveList: BaseRes<List<DeserveBean>>
    )
}