package com.zhuzichu.orange.shopdetail.fragment

import com.zhuzichu.mvvm.base.BaseFragment
import com.zhuzichu.orange.BR
import com.zhuzichu.orange.R
import com.zhuzichu.orange.databinding.FragmentShopDetailBinding
import com.zhuzichu.orange.shopdetail.viewmodel.ShopDetailViewModel

class ShopDetailFragment : BaseFragment<FragmentShopDetailBinding, ShopDetailViewModel>() {
    override fun setLayoutId(): Int = R.layout.fragment_shop_detail

    override fun bindVariableId(): Int = BR.viewModel
}