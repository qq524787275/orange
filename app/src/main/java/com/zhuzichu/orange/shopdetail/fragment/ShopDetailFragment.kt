package com.zhuzichu.orange.shopdetail.fragment

import androidx.fragment.app.Fragment
import com.zhuzichu.mvvm.base.BaseTopBarFragment
import com.zhuzichu.mvvm.utils.bindArgument
import com.zhuzichu.mvvm.utils.initMagicIndicator
import com.zhuzichu.mvvm.utils.toColorById
import com.zhuzichu.orange.BR
import com.zhuzichu.orange.R
import com.zhuzichu.orange.databinding.FragmentShopDetailBinding
import com.zhuzichu.orange.shopdetail.adapter.ShopDetailFragmentPageAdapter
import com.zhuzichu.orange.shopdetail.viewmodel.ShopDetailViewModel
import kotlinx.android.synthetic.main.fragment_shop_detail.*

class ShopDetailFragment : BaseTopBarFragment<FragmentShopDetailBinding, ShopDetailViewModel>() {
    private val itemid: String by bindArgument(ITEMID)
    private val type: String by bindArgument(TYPE)
    val titles = listOf("宝贝", "详情")

    companion object {
        const val ITEMID = "itemid"
        const val TYPE = "type"
    }

    override fun setLayoutId(): Int = R.layout.fragment_shop_detail

    override fun bindVariableId(): Int = BR.viewModel

    override fun initView() {
        setStatusBarColor(R.color.white.toColorById())
        initViewPager()
    }

    private fun initViewPager() {
        val fragments = listOf<Fragment>(ShopDetailOneFragment(itemid,type), ShopDetailTwoFragment(itemid,type))
        pager.adapter = ShopDetailFragmentPageAdapter(childFragmentManager, fragments)
        pager.offscreenPageLimit = titles.size
        initMagicIndicator(
            activity, titles, pager, indicator
        )
    }
}