package com.zhuzichu.orange.repository

import android.util.Log
import com.zhuzichu.mvvm.base.BaseRes
import com.zhuzichu.orange.bean.*
import com.zhuzichu.orange.http.IService
import io.reactivex.Flowable
import io.reactivex.Observable
import org.jsoup.Jsoup

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-05-28
 * Time: 18:11
 */
object NetRepositoryImpl : NetRepository, IService {

    override fun getShopDetailDesc(itemid: String, type: String): Flowable<String> {
        //B天猫 C淘宝
        when (type) {
            "C" -> {
                return getTaobaoService().getShopDetailDesc(itemid)
                    .map {
                        Log.i("zzc",it)
                        val desc = Jsoup.parse(it).select(".detail-desc").first()
                        if (desc != null) {
                            desc.html()
                        } else {
                            "数据异常"
                        }
                    }
            }
            "B" -> {
                return getTmallService().getShopDetailDesc(itemid)
                    .map {
                        val desc = Jsoup.parse(it).select("div.container").first()
                        if (desc != null) {
                            desc.html()
                        } else {
                            "数据异常"
                        }
                    }
            }
            else -> {
                return Flowable.just("数据异常")
            }
        }
    }

    override fun getShopDetail(itemid: String): Flowable<BaseRes<ShopDetailBean>> {
        return getHaoDankuService().getShopDetail(itemid)
    }

    override fun getSubjectHotList(min_id: Int): Flowable<BaseRes<List<SubjectHotBean>>> {
        return getHaoDankuService().getSubjectHotList(min_id)
    }

    override fun getSubjectItemList(id: String): Flowable<BaseRes<List<SubjectItemBean>>> {
        return getHaoDankuService().getSubjectItemList(id)
    }

    override fun getSubjectList(): Flowable<BaseRes<List<SubjectBean>>> {
        return getHaoDankuService().getSubjectList()
    }

    override fun getSelectedItemList(min_id: Int): Flowable<BaseRes<List<SelectedItemBean>>> {
        return getHaoDankuService().getSelectedItemList(min_id)
    }

    override fun getVideoList(
        cid: Int,
        back: Int,
        min_id: Int
    ): Flowable<BaseRes<List<ShopBean>>> {
        return getHaoDankuService().getShopList(4, cid, back, min_id)
    }

    override fun getTalentcat(): Flowable<BaseRes<TalentcatBean>> {
        return getHaoDankuService().getTalentcat()
    }

    override fun getBrandList(back: Int, min_id: Int): Flowable<BaseRes<List<BrandBean>>> {
        return getHaoDankuService().getBrandList(back, min_id)
    }

    override fun getDeserveList(): Flowable<BaseRes<List<DeserveBean>>> {
        return getHaoDankuService().getDeserveList()
    }

    override fun getHotKeyList(): Flowable<BaseRes<List<HotKeyBean>>> {
        return getHaoDankuService().getHotKeyList()
    }

    override fun getSalersList(
        back: Int,
        sale_type: Int,
        min_id: Int
    ): Flowable<BaseRes<List<SalesBean>>> {
        return getHaoDankuService().getSalersList(back, sale_type, min_id)
    }

    override fun searchShop(
        keyword: String,
        back: Int,
        sort: Int,
        cid: Int,
        min_id: Int
    ): Flowable<BaseRes<List<SearchBean>>> {
        return getHaoDankuService().searchShop(keyword, back, sort, cid, min_id)
    }

    override fun getShopSort(): Flowable<BaseRes<List<SortBean>>> {
        return getHaoDankuService().getShopSort()
    }

    override fun getShopList(
        nav: Int,
        cid: Int,
        back: Int,
        min_id: Int
    ): Flowable<BaseRes<List<ShopBean>>> {
        return getHaoDankuService().getShopList(nav, cid, back, min_id)
    }


}