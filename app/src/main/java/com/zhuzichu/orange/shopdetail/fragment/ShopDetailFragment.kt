package com.zhuzichu.orange.shopdetail.fragment

import android.os.Bundle
import com.zhuzichu.mvvm.base.BaseFragment
import com.zhuzichu.mvvm.utils.bindArgument
import com.zhuzichu.orange.BR
import com.zhuzichu.orange.R
import com.zhuzichu.orange.databinding.FragmentShopDetailBinding
import com.zhuzichu.orange.shopdetail.viewmodel.ShopDetailViewModel

class ShopDetailFragment : BaseFragment<FragmentShopDetailBinding, ShopDetailViewModel>() {
    private val itemid: String by bindArgument(ITEMID)
    private val type: String by bindArgument(TYPE)

    companion object {
        const val ITEMID = "itemid"
        const val TYPE = "type"
    }

    override fun setLayoutId(): Int = R.layout.fragment_shop_detail

    override fun bindVariableId(): Int = BR.viewModel

    override fun onEnterAnimationEnd(savedInstanceState: Bundle?) {
//        _viewModel.loadShopDetail(itemid)
        _viewModel.loadShopDetailDesc(itemid, type)
    }
}