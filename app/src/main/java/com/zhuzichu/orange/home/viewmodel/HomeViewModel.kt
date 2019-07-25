package com.zhuzichu.orange.home.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import androidx.databinding.ObservableArrayList
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.GridLayoutManager
import com.zhuzichu.mvvm.base.BaseRes
import com.zhuzichu.mvvm.base.BaseViewModel
import com.zhuzichu.mvvm.bus.event.SingleLiveEvent
import com.zhuzichu.mvvm.databinding.command.BindingCommand
import com.zhuzichu.mvvm.global.color.ColorGlobal
import com.zhuzichu.mvvm.utils.*
import com.zhuzichu.orange.BR
import com.zhuzichu.orange.R
import com.zhuzichu.orange.bean.DeserveBean
import com.zhuzichu.orange.bean.SalesBean
import com.zhuzichu.orange.bean.ShopBean
import com.zhuzichu.orange.itemDiff
import com.zhuzichu.orange.repository.NetRepositoryImpl
import com.zhuzichu.orange.search.fragment.SearchFragment
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Function4
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
    val color = ColorGlobal
    val uc = UIChangeObservable()

    inner class UIChangeObservable {
        val finishRefreshing = SingleLiveEvent<Any>()
        val finishLoadmore = SingleLiveEvent<Any>()
        val finishLoadMoreWithNoMoreData = SingleLiveEvent<Any>()
    }

    val listBanner = MutableLiveData<List<Any>>()

    val itemBindBanner = itemBindingOf<Any>(BR.item, R.layout.item_home_banner)


    val itemBindNavigation =
        itemBindingOf<ItemNavigationViewModel>(BR.item, R.layout.item_home_navigation)
    val listNavigation = ObservableArrayList<ItemNavigationViewModel>().apply {
        addAll(
            listOf(
                ItemNavigationViewModel(this@HomeViewModel, "榜单", R.mipmap.a1),
                ItemNavigationViewModel(this@HomeViewModel, "品牌", R.mipmap.a2),
                ItemNavigationViewModel(this@HomeViewModel, "抢购", R.mipmap.a3),
                ItemNavigationViewModel(this@HomeViewModel, "抖商", R.mipmap.a4),
                ItemNavigationViewModel(this@HomeViewModel, "达人说", R.mipmap.a5),
                ItemNavigationViewModel(this@HomeViewModel, "xxx", R.mipmap.a6),
                ItemNavigationViewModel(this@HomeViewModel, "xxx", R.mipmap.a7),
                ItemNavigationViewModel(this@HomeViewModel, "xxx", R.mipmap.a8),
                ItemNavigationViewModel(this@HomeViewModel, "xxx", R.mipmap.a9),
                ItemNavigationViewModel(this@HomeViewModel, "xxx", R.mipmap.a10)
            )
        )
    }

    val itemBind = OnItemBindClass<Any>().apply {
        map<ItemHomeClassViewModel>(BR.item, R.layout.item_home_class)
        map<ItemHomeDeserveViewModel>(BR.item, R.layout.item_home_deserve)
        map<ItemHomeHotViewModel>(BR.item, R.layout.item_home_hot)
        map<ItemHomeJuTaoVIewModel>(BR.item, R.layout.item_home_jutao)
    }.toItemBinding()

    private val deserveList = AsyncDiffObservableList(itemDiff<ItemHomeDeserveViewModel> { oldItem, newItem ->
        oldItem.deserveBean.itemid == newItem.deserveBean.itemid
    })

    private val hotShopList = AsyncDiffObservableList(itemDiff<ItemHomeHotViewModel> { oldItem, newItem ->
        oldItem.shopBean.itemid == newItem.shopBean.itemid
    })

    private val juTaoShopList = AsyncDiffObservableList(itemDiff<ItemHomeHotViewModel> { oldItem, newItem ->
        oldItem.shopBean.itemid == newItem.shopBean.itemid
    })

    val list: MergeObservableList<Any> = MergeObservableList<Any>()
        .insertItem(ItemHomeClassViewModel(this@HomeViewModel, "今日值得买", deserveList, R.drawable.background02))
        .insertList(deserveList)
        .insertItem(ItemHomeClassViewModel(this@HomeViewModel, "爆单系列", hotShopList, R.drawable.background01))
        .insertList(hotShopList)
        .insertItem(ItemHomeClassViewModel(this@HomeViewModel, "聚淘专区", juTaoShopList, R.drawable.background03))
        .insertList(juTaoShopList)

    val spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
        override fun getSpanSize(position: Int): Int {
            val item = list[position]
            if (item is ItemHomeClassViewModel)
                return 2
            if (item is ItemHomeJuTaoVIewModel)
                return 2
            return 1
        }
    }


    val onClickSearch = BindingCommand<Any>({
        startFragment(SearchFragment(), launchMode = ISupportFragment.SINGLETASK)
    })

    fun loadHomeData() {
        Flowable.zip(
            NetRepositoryImpl.getHomeBannerList().subscribeOn(Schedulers.io()).compose(exceptionTransformer()).compose(
                bindToLifecycle(getLifecycleProvider())
            ),
            NetRepositoryImpl.getDeserveList().subscribeOn(Schedulers.io()).compose(exceptionTransformer()).compose(
                bindToLifecycle(getLifecycleProvider())
            ),
            NetRepositoryImpl.getHomeHotShopList().subscribeOn(Schedulers.io()).compose(exceptionTransformer()).compose(
                bindToLifecycle(getLifecycleProvider())
            ).take(12),
            NetRepositoryImpl.getHomeJuTaoShopList().subscribeOn(Schedulers.io()).compose(exceptionTransformer()).compose(
                bindToLifecycle(getLifecycleProvider())
            ).take(12),
            Function4<BaseRes<List<SalesBean>>, BaseRes<List<DeserveBean>>, BaseRes<List<ShopBean>>, BaseRes<List<ShopBean>>, HomeData> { t1, t2, t3, t4 ->
                HomeData(t1, t2, t3, t4)
            }
        ).observeOn(AndroidSchedulers.mainThread())
            .compose(bindToLifecycle(getLifecycleProvider()))
            .doFinally {
                uc.finishRefreshing.call()
            }
            .subscribe({
                listBanner.value = it.salesList.data.map { item ->
                    ItemHomeBannerViewModel(this@HomeViewModel, item)
                }

                deserveList.update(it.deserveList.item_info.map { item ->
                    ItemHomeDeserveViewModel(this@HomeViewModel, item)
                })

                hotShopList.update(it.hotList.data.map { item ->
                    ItemHomeHotViewModel(this@HomeViewModel, item)
                })

                juTaoShopList.update(it.jutaoList.data.map { item ->
                    ItemHomeJuTaoVIewModel(this@HomeViewModel, item)
                })
            }, {
                handleThrowable(it)
            })
    }

    data class HomeData(
        var salesList: BaseRes<List<SalesBean>>,
        var deserveList: BaseRes<List<DeserveBean>>,
        var hotList: BaseRes<List<ShopBean>>,
        var jutaoList: BaseRes<List<ShopBean>>
    )
}