package com.zhuzichu.orange.shopdetail.fragment

import androidx.fragment.app.Fragment
import com.zhuzichu.mvvm.base.BaseTopbarFragment
import com.zhuzichu.mvvm.utils.bindArgument
import com.zhuzichu.mvvm.utils.initMagicIndicator
import com.zhuzichu.mvvm.utils.toColorById
import com.zhuzichu.orange.BR
import com.zhuzichu.orange.R
import com.zhuzichu.orange.databinding.FragmentShopDetailBinding
import com.zhuzichu.orange.shopdetail.adapter.ShopDetailFragmentPageAdapter
import com.zhuzichu.orange.shopdetail.viewmodel.ShopDetailViewModel
import kotlinx.android.synthetic.main.fragment_shop_detail.*

class ShopDetailFragment : BaseTopbarFragment<FragmentShopDetailBinding, ShopDetailViewModel>() {
    private val itemid: String by bindArgument(ITEMID)
    private val type: String by bindArgument(TYPE)
    private val itemprice: String by bindArgument(ITEMPRICE)
    private val itemendprice: String by bindArgument(ITEMENDPRICE)

    val titles = listOf("宝贝详情")

    companion object {
        const val ITEMID = "itemid"
        const val TYPE = "type"
        const val ITEMPRICE = "itemprice"
        const val ITEMENDPRICE = "itemendprice"
    }

    override fun setLayoutId(): Int = R.layout.fragment_shop_detail

    override fun bindVariableId(): Int = BR.viewModel

    override fun initView() {
        setStatusBarColor(R.color.white.toColorById())
        initViewPager()
        _viewModel.itemid.set(itemid)
        _viewModel.itemprice.set(itemprice)
        _viewModel.itemendprice.set(itemendprice)
    }

    private fun initViewPager() {
        val fragments = listOf<Fragment>(ShopDetailOneFragment(itemid, type))
        pager.adapter = ShopDetailFragmentPageAdapter(childFragmentManager, fragments)
        pager.offscreenPageLimit = titles.size
        initMagicIndicator(
            activity, titles, pager, indicator
        )
    }
}