package com.zhuzichu.orange.goods.fragment

import android.os.Bundle
import com.zhuzichu.mvvm.base.BaseFragment
import com.zhuzichu.mvvm.bean.GoodsBean
import com.zhuzichu.mvvm.utils.*
import com.zhuzichu.mvvm.view.banner.ScaleLayoutManager
import com.zhuzichu.orange.BR
import com.zhuzichu.orange.R
import com.zhuzichu.orange.databinding.FragmentGoodsBinding
import com.zhuzichu.orange.goods.viewmodel.GoodsViewModel
import com.zhuzichu.orange.goods.viewmodel.ItemGoodsBannerViewModel
import kotlinx.android.synthetic.main.fragment_goods.*


/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-08-21
 * Time: 11:19
 */
class GoodsFragment : BaseFragment<FragmentGoodsBinding, GoodsViewModel>() {

    companion object {
        const val GOODS_INFO = "GOODS_INFO"
    }

    private val info: GoodsBean.TbkDgMaterialOptionalResponse.ResultList.MapData by bindArgument(GOODS_INFO)

    override fun setLayoutId(): Int = R.layout.fragment_goods

    override fun bindVariableId(): Int = BR.viewModel

    override fun initView() {
        _viewModel.itemid.set(info.item_id.toString())
        _viewModel.itemprice.set(info.zk_final_price)
        _viewModel.itemendprice.set((info.zk_final_price.toDouble() - info.coupon_amount.toDouble()).format2())
        var iconId = R.mipmap.ic_taobao
        if (info.user_type == 1)
            iconId = R.mipmap.ic_tmall
        val text = "".spannable().append(iconId, 30, 30).append(" ").append(info.title)
        _viewModel.title.set(text)
        initBanner()
    }


    private fun initBanner() {
        val scaleLayoutManager = ScaleLayoutManager(_activity, dip2px(5f))
        scaleLayoutManager.minScale = 0.9f
        banner.layoutManager = scaleLayoutManager

    }

    override fun onEnterAnimationEnd(savedInstanceState: Bundle?) {
        super.onEnterAnimationEnd(savedInstanceState)
        info.small_images.string.size.toast()
        _viewModel.bannerList.update(info.small_images.string.map {
            ItemGoodsBannerViewModel(_viewModel, it.plus("_500x500.jpg"))
        })
        view?.postDelayed({
            dots.attachRecyclerView(banner)
        }, 150)
    }
}