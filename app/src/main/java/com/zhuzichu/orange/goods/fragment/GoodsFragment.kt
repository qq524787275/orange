package com.zhuzichu.orange.goods.fragment

import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.scwang.smartrefresh.layout.util.SmartUtil
import com.zhuzichu.mvvm.base.BaseFragment
import com.zhuzichu.mvvm.bean.GoodsBean
import com.zhuzichu.mvvm.global.AppGlobal
import com.zhuzichu.mvvm.global.color.ColorGlobal
import com.zhuzichu.mvvm.utils.append
import com.zhuzichu.mvvm.utils.bindArgument
import com.zhuzichu.mvvm.utils.spannable
import com.zhuzichu.orange.BR
import com.zhuzichu.orange.R
import com.zhuzichu.orange.databinding.FragmentGoodsBinding
import com.zhuzichu.orange.goods.viewmodel.GoodsViewModel
import com.zhuzichu.orange.utils.isTrue
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

    private val info: GoodsBean by bindArgument(GOODS_INFO)

    override fun setLayoutId(): Int = R.layout.fragment_goods

    override fun bindVariableId(): Int = BR.viewModel

    private var mScrollY = 0


    override fun initView() {
        _viewModel.url = info.couponurl
        _viewModel.itemid = info.itemid
        _viewModel.itemprice.set(info.itemprice)
        _viewModel.itemendprice.set(info.itemendprice)
        var iconId = R.mipmap.ic_taobao
        if (info.shoptype == "B")
            iconId = R.mipmap.ic_tmall
        recycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            private var lastScrollY = 0
            private val h = SmartUtil.dp2px(80f)
            private val color: Int = ColorGlobal.colorPrimary.value!! and 0x00ffffff
            private var totalDy = 0

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                totalDy += dy
                var y = totalDy
                if (lastScrollY < h) {
                    y = h.coerceAtMost(y)
                    mScrollY = if (y > h) h else y
                    title.alpha = 1f * mScrollY / h
                    topbar.setBackgroundColor(255 * mScrollY / h shl 24 or color)
                }
                lastScrollY = y
            }
        })

        val text = "".spannable().append(iconId, 30, 30).append(" ").append(info.itemtitle)
        _viewModel.headerViewModel.title.set(text)
        view?.post {
            _viewModel.headerViewModel.updateBanner(info.smallimages)
        }

        AppGlobal.isLogin.get().isTrue {

        }
    }


    override fun onEnterAnimationEnd(savedInstanceState: Bundle?) {
        _viewModel.loadRecommendData()
    }
}