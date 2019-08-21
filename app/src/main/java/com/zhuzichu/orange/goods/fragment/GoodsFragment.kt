package com.zhuzichu.orange.goods.fragment

import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.core.widget.NestedScrollView
import com.ali.auth.third.core.context.KernelContext.getApplicationContext
import com.scwang.smartrefresh.layout.util.SmartUtil
import com.zhuzichu.mvvm.base.BaseFragment
import com.zhuzichu.mvvm.bean.GoodsBean
import com.zhuzichu.mvvm.global.color.ColorGlobal
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

    private var mScrollY = 0

    override fun initView() {
        _viewModel.url = info.coupon_share_url
        _viewModel.itemid.set(info.item_id.toString())
        _viewModel.itemprice.set(info.zk_final_price)
        _viewModel.itemendprice.set((info.zk_final_price.toDouble() - info.coupon_amount.toDouble()).format2())
        var iconId = R.mipmap.ic_taobao
        if (info.user_type == 1)
            iconId = R.mipmap.ic_tmall
        val text = "".spannable().append(iconId, 30, 30).append(" ").append(info.title)
        _viewModel.title.set(text)
        initBanner()

        scroll.setOnScrollChangeListener(object : NestedScrollView.OnScrollChangeListener {
            private var lastScrollY = 0
            private val h = SmartUtil.dp2px(80f)
            private val color: Int = ColorGlobal.colorPrimary.value!! and 0x00ffffff
            override fun onScrollChange(
                v: NestedScrollView?,
                scrollX: Int,
                scrollY: Int,
                oldScrollX: Int,
                oldScrollY: Int
            ) {
                var y = scrollY
                if (lastScrollY < h) {
                    y = h.coerceAtMost(y)
                    mScrollY = if (y > h) h else y
                    title.alpha = 1f * mScrollY / h
                    topbar.setBackgroundColor(255 * mScrollY / h shl 24 or color)
                }
                lastScrollY = y
            }
        })
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