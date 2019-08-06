package com.zhuzichu.mvvm.repository

import com.zhuzichu.mvvm.bean.*
import com.zhuzichu.mvvm.service.IService
import io.reactivex.Flowable


/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-05-28
 * Time: 18:11
 */
object NetRepositoryImpl : NetRepository, IService {
    override fun updateUserInfo(type: Int, value: Any): Flowable<BaseRes<UserInfoBean>> {
        return getAppService().updateUserInfo(type, value)
    }

    override fun getUserInfo(): Flowable<BaseRes<UserInfoBean>> {
        return getAppService(isEncrypt = false).getUserInfo()
    }

    override fun login(username: String, password: String): Flowable<BaseRes<TokenBean>> {
        return getAppService().login(username, password)
    }

    override fun getRegistCode(phone: String): Flowable<BaseRes<String>> {
        return getAppService(isEncrypt = false).getRegistCode(phone)
    }

    override fun regist(username: String, password: String, phone: String, code: String): Flowable<BaseRes<TokenBean>> {
        return getAppService().regist(username, password, phone, code)
    }

    override fun getHomeBannerList(): Flowable<BaseRes<List<SalesBean>>> {
        return getSalersList(5, 1, 1)
    }

    override fun getHomeJuTaoShopList(): Flowable<BaseRes<List<ShopBean>>> {
        return getShopList(2, 0, 20, 1)
    }

    override fun getHomeHotShopList(): Flowable<BaseRes<List<ShopBean>>> {
        return getShopList(5, 0, 20, 1)
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